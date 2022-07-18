/**
 * URL 파서 컨트롤러
 * **/
class UrlParser {
    constructor() {
        this.sch = location.search;
    }
}

/**
 * 각종 기능 유틸리티 컨트롤러
 * **/
class UtilController {
    constructor() {
        this.initHandlerbars();
    }

    initHandlerbars() {
        Handlebars.registerHelper('isDiscountZero', discount => {
            return discount <= 0 ? true : false;
        });
    }

    /** 오픈소스 참조 (로딩 중 화면 만들기) **/
    loadingWithMask(width, height) {
        //화면의 높이와 너비를 구합니다.
        const maskHeight = height;
        const maskWidth = width;

        //화면에 출력할 마스크를 설정해줍니다.
        const mask = `<div id='mask' style='position:absolute; z-index:9000; background-color:#000000; display:none; left:0; top:0;'></div>`;
        let loadingImg = ``;

        loadingImg += "<div id='loadingImg' style='position:absolute; top: calc(50% - (200px / 2)); width:100%; z-index:99999999;'>";
        loadingImg += `<img src='../image/loading_img.gif' style='position: relative; display: block; margin: 0px auto;'/>`;
        loadingImg += `</div>`;

        //화면에 레이어 추가
        $('body')
            .append(mask)
            .append(loadingImg)

        //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
        $('#mask').css({
            'width': maskWidth
            , 'height': maskHeight
            , 'opacity': '0.3'
        });

        //마스크 표시
        $('#mask').show();

        //로딩중 이미지 표시
        $('#loadingImg').show();
    }

    /** 오픈소스 참조 (로딩 중 화면 닫기) **/
    closeLoadingWithMask() {
        $('#mask, #loadingImg').hide();
        $('#mask, #loadingImg').empty();
    }

    showToastMessage(message) {
        Toastify({
            text: message,
            duration: 3000,
            close: true,
            position: "center",
            stopOnFocus: true,
            style: {
                background: "linear-gradient(to right, #00b09b, #96c93d)",
            }
        }).showToast();
    }
}

class BannerController {
    initBannerController() {
        this.requestHttpBannerImage();
    }

    requestHttpBannerImage() {
        this.fetchBannerImage("/banner-image").then(bannerImageData => {
            if (bannerImageData !== undefined) {
                const bannerImageListTemplate = document.querySelector("#banner-image-list-template").innerHTML;
                const template = Handlebars.compile(bannerImageListTemplate);
                const templateHTML = template({bannerList: bannerImageData});
                document.getElementById("banner-carousel-board").innerHTML += templateHTML;
                this.bannerCarouselController = new BannerCarouselController();
                this.bannerCarouselController.initBannerCarouselController();
            }
        });
    }

    async fetchBannerImage(url) {
        try {
            const response = await fetch(url, {method: 'GET'});
            const responseJson = await response.json();
            return responseJson;
        } catch (error) {
            console.log("[BannerController:fetchBannerImage] error =>", err);
            return undefined;
        }
    }
}

/**
 * banner-carousel controller
 * **/
class BannerCarouselController extends UtilController {
    constructor() {
        super();
        this.bannerCarouselBoard = document.getElementById("banner-carousel-board");
        this.pauseButton = document.getElementById("carousel-pause-button");
        this.moreButton = document.getElementById("carousel-more-button");
        this.backwardButton = document.getElementById("carousel-backward-button");
        this.forwardButton = document.getElementById("carousel-forward-button");
        this.bannerCurrentCount = document.getElementById("banner-current-count");
        this.bannerTotalCount = document.getElementById("banner-total-count");
        this.prevBannerIndex = 0;
        this.currentBannerIndex = this.prevBannerIndex;
        this.bannerCarouselBoardList = this.bannerCarouselBoard.querySelectorAll("li");
        if (this.bannerCarouselBoardList.length <= 0) {
            document.getElementById("carousel-default-thumbnail").style.display = 'block';
            this.pauseButton.style.backgroundImage = "url(../image/start.png)";
            this.bannerCurrentCount.innerText = `${this.currentBannerIndex}`;
            this.carouselRunnerFlag = false;
        } else {
            this.bannerCarouselBoardList[this.currentBannerIndex].style.display = 'block';
            this.bannerCurrentCount.innerText = `0${this.currentBannerIndex + 1}`;
            this.carouselRunnerFlag = true;
        }
        this.totalBannerSize = this.bannerCarouselBoardList.length;
        this.bannerTotalCount.innerText = this.totalBannerSize < 10 ? `0${this.totalBannerSize}` : `${this.totalBannerSize}`;
        this.carouselDelayTime = 3500;
        this.timeoutId = 0;
    }

    initBannerCarouselController() {
        if (this.carouselRunnerFlag === true) {
            this.initCarouselRunner();
            this.initCarouselButtonListener();
        }
    }

    initCarouselRunner() {
        this.carouselRunner(this.prevBannerIndex);
    }

    initCarouselButtonListener() {
        this.pauseButton.addEventListener("click", () => {
            if (this.carouselRunnerFlag === true) {
                this.carouselRunnerFlag = false;
                this.pauseButton.style.backgroundImage = "url(../image/start.png)";
                clearTimeout(this.timeoutId);
            } else {
                this.carouselRunnerFlag = true;
                this.pauseButton.style.backgroundImage = "url(../image/pause.png)";
                this.carouselRunner(this.prevBannerIndex);
            }
        });

        this.moreButton.addEventListener("click", () => {
            // TODO, 전체 광고 배너 이미지 보여주는 사이트로 이동 or 새창 OPEN
            this.showToastMessage("더보기 버튼 입니다. 추후에 구현 해볼 예정");
        });

        this.backwardButton.addEventListener("click", () => {
            this.moveCarousel(true);
            clearTimeout(this.timeoutId);

            if (this.carouselRunnerFlag === true) {
                this.carouselRunner();
            }
        });

        this.forwardButton.addEventListener("click", () => {
            this.moveCarousel(false);
            clearTimeout(this.timeoutId);

            if (this.carouselRunnerFlag === true) {
                this.carouselRunner();
            }
        });
    }

    carouselRunner() {
        this.timeoutId = setTimeout(() => {
            this.prevBannerIndex = this.currentBannerIndex;

            if (this.bannerCarouselBoardList[this.prevBannerIndex].style.display === 'block') {
                this.bannerCarouselBoardList[this.prevBannerIndex].style.display = '';
            }

            this.moveCarousel(false);

            if (this.bannerCarouselBoardList[this.currentBannerIndex].style.display === '') {
                this.bannerCarouselBoardList[this.currentBannerIndex].style.display = 'block';
            }
            this.carouselRunner();
        }, this.carouselDelayTime);
    }

    moveCarousel(isLeft) {
        this.prevBannerIndex = this.currentBannerIndex;

        if (this.bannerCarouselBoardList[this.prevBannerIndex].style.display === 'block') {
            this.bannerCarouselBoardList[this.prevBannerIndex].style.display = '';
        }

        if (isLeft === true) {
            // left
            this.currentBannerIndex--;

            if (this.currentBannerIndex < 0) {
                this.currentBannerIndex = this.totalBannerSize - 1;
            }
        } else {
            // right
            this.currentBannerIndex++;

            if (this.currentBannerIndex >= this.totalBannerSize) {
                this.currentBannerIndex = 0;
            }
        }

        if (this.currentBannerIndex + 1 < 10) {
            this.bannerCurrentCount.innerText = `0${this.currentBannerIndex + 1}`;
        } else {
            this.bannerCurrentCount.innerText = `${this.currentBannerIndex + 1}`;
        }

        if (this.bannerCarouselBoardList[this.currentBannerIndex].style.display === '') {
            this.bannerCarouselBoardList[this.currentBannerIndex].style.display = 'block';
        }
    }
}

/**
 * product-category controller
 * **/
class ProductCategoryController {
    constructor() {
        this.productCategoryList = document.getElementById("product_category_list");
        this.prevSelectedCategory = null;
        this.defaultImageDiv = document.getElementById("default_image_div");
        this.productCardController = new ProductCardController();
    }

    initProductCategoryController() {
        this.setProductCategoryListEventListener();
        this.requestHttpCategory();
    }

    requestHttpCategory() {
        this.fetchCategory("/category/product").then(categoryData => {
            if (categoryData !== undefined) {
                const productCategoryListTemplate = document.querySelector("#product-category-list-template").innerHTML;
                const template = Handlebars.compile(productCategoryListTemplate);
                const templateHTML = template({productCategoryList: categoryData});
                this.productCategoryList.innerHTML += templateHTML;
            }
        });
    }

    async fetchCategory(url) {
        try {
            const response = await fetch(url, {method: 'GET'});
            const responseJson = await response.json();
            return responseJson;
        } catch (error) {
            console.log("[ProductCategoryController:fetchCategory] error =>", err);
            return undefined;
        }
    }

    setProductCategoryListEventListener() {
        this.productCategoryList.addEventListener("click", event => {
            this.setCategoryClickEvent(event.target);
            this.setDefaultImage();
        });

        this.productCardController.setEventHandlerListener();
    }

    setCategoryClickEvent(target) {
        if (this.prevSelectedCategory != null) {
            this.prevSelectedCategory.style.background = 'white';
            this.prevSelectedCategory.style.color = 'black';
        }

        const selectedCategory = target.closest("li");
        selectedCategory.style.background = 'black';
        selectedCategory.style.color = 'white';
        this.prevSelectedCategory = selectedCategory;

        this.productCardController.requestHttpProduct(selectedCategory.getAttribute("categoryId"));
    }

    setDefaultImage() {
        if (this.prevSelectedCategory === null) {
            this.defaultImageDiv.style.display = 'block';
        } else {
            this.defaultImageDiv.style.display = 'none';
        }
    }
}

/**
 *  product-card controller
 * **/
class ProductCardController extends UtilController {
    constructor() {
        super();
        this.templateMap = new Map();
        this.curCategoryId = -1;
        this.productCardList = document.getElementById("product_list");
        this.emptyProdImage = document.getElementById("empty_image_div");
        this.productMoreShowBtn = document.getElementById("moreProductBtn");
    }

    setEventHandlerListener() {
        this.productMoreShowBtn.addEventListener("click", event => {
            this.showToastMessage("상품 더보기 버튼 기능 구현 예정");
        });
    }

    requestHttpProduct(categoryId) {
        if (this.curCategoryId === categoryId) {
            return;
        }

        if (!this.templateMap.get(categoryId)) {
            this.fetchProduct(`/product/category?categoryId=${categoryId}`).then(productData => {
                if (productData !== undefined) {
                    if (productData.length <= 0) {
                        this.emptyProdImage.style.display = 'block';
                        this.productMoreShowBtn.style.display = 'none';
                        this.productCardList.innerHTML = this.emptyProdImage.outerHTML;
                    } else {
                        const productListTemplate = document.querySelector("#product-card-template").innerHTML;
                        const template = Handlebars.compile(productListTemplate);
                        const templateHTML = template({productList: productData});
                        this.productCardList.innerHTML = templateHTML;
                        this.emptyProdImage.style.display = 'none';
                        this.productMoreShowBtn.style.display = 'block';
                    }
                    this.templateMap.set(categoryId, this.productCardList.innerHTML);
                }
            });
        } else {
            this.productCardList.innerHTML = this.templateMap.get(categoryId);
        }

        this.curCategoryId = categoryId;
    }

    async fetchProduct(url) {
        try {
            const response = await fetch(url, {method: 'GET'});
            const responseJson = await response.json();
            return responseJson;
        } catch (error) {
            console.log("[ProductCardController:fetchProduct] error =>", err);
            return undefined;
        }
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const bannerController = new BannerController();
    const productCategoryController = new ProductCategoryController();

    bannerController.initBannerController();
    productCategoryController.initProductCategoryController();
});