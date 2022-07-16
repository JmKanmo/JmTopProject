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
        this.emailRegExp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        this.callRegExp = /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}/;
        this.urlRegExp = /(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
        this.MAX_PRODUCT_IMAGE_COUNT = 5;
    }

    /** 오픈소스 참조 (로딩 중 화면 만들기) **/
    loadingWithMask() {
        //화면의 높이와 너비를 구합니다.
        const maskHeight = $(document).height();
        const maskWidth = window.document.body.clientWidth;

        //화면에 출력할 마스크를 설정해줍니다.
        const mask = `<div id='mask' style='position:absolute; z-index:9000; background-color:#000000; display:none; left:0; top:0;'></div>`;
        let loadingImg = ``;

        loadingImg += `<div id='loadingImg'>`;
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

/**
 * 이미지 기능 관련 컨트롤러
 * **/
class ImageController extends UtilController {
    constructor() {
        super();
        this.bannerImageForm = document.forms["banner_image_form"];
        this.bannerImageInput = document.getElementById("banner_image");
        this.bannerThumbnailImg = document.getElementById("banner_thumbnail_img");
        this.bannerResetImageBtn = document.getElementById("banner_reset_img_btn");

        this.productImageForm = document.forms["product_image_form"];
        this.productImageInput = document.getElementById("product_image");
        this.productThumbnailImageUl = document.getElementById("product_thumbnail_image_ul");
        this.productResetImageBtn = document.getElementById("product_reset_img_btn");
    }

    initImageController() {
        this.initProductImageController();
        this.initBannerImageController();
    }

    initProductImageController() {
        this.updateProductThumbnailImage();
        this.setProductResetImgBtnClickListener();
    }

    initBannerImageController() {
        this.updateBannerThumbnailImage();
        this.setBannerResetImgBtnClickListener();
    }

    updateBannerThumbnailImage() {
        const bannerImageInput = this.bannerImageInput;
        bannerImageInput.addEventListener("change", event => {
            this.readBannerImage(event.target);
        });
    }

    updateProductThumbnailImage() {
        const productImageInput = this.productImageInput;
        productImageInput.addEventListener("change", event => {
            this.readProductImage(event.target);
        });
    }

    setBannerResetImgBtnClickListener() {
        const bannerResetImageBtn = this.bannerResetImageBtn;
        bannerResetImageBtn.addEventListener("click", () => {
            if (confirm("이미지를 초기화 하시겠습니까?")) {
                this.resetBannerImage();
            }
        });
    }

    setProductResetImgBtnClickListener() {
        const productResetImageBtn = this.productResetImageBtn;
        productResetImageBtn.addEventListener("click", () => {
            if (confirm("이미지를 초기화 하시겠습니까?")) {
                this.resetProductImage();
            }
        });
    }

    readBannerImage(input) {
        // TODO
        let imgFile = input.files;

        if (imgFile && imgFile[0]) {
            let fileForms = ['jpg', 'jpeg', 'png', 'gif', 'GIF'];
            let fileSize = 50 * 1024 * 1024;
            let fileExtension = input.value.slice(input.value.lastIndexOf(".") + 1)

            if (!fileForms.includes(fileExtension)) {
                this.showToastMessage("지정 된 이미지 파일만 업로드 가능합니다.");
                this.resetBannerImage();
                return;
            } else if (imgFile[0].size >= fileSize) {
                this.showToastMessage('최대 파일 사이즈는 50MB 입니다.');
                this.resetBannerImage();
                return;
            }

            const fileReader = new FileReader();

            fileReader.onload = (event) => {
                const xhr = new XMLHttpRequest();

                xhr.open("POST", "/register/image" + "?dest=temp");

                xhr.addEventListener("loadend", event => {
                    let status = event.target.status;

                    if (status >= 400 && status <= 500) {
                        const responseJson = JSON.parse(event.target.responseText);
                        const responseJsonText = JSON.stringify(responseJson, null, 4);
                        alert(`배너 이미지 저장 작업 중에 에러 발생!! \n\n error message: ${responseJsonText}`);
                        this.resetBannerImage();
                    } else {
                        const responseValue = event.target.responseText;
                        this.bannerThumbnailImg.src = responseValue;
                    }
                    this.closeLoadingWithMask();
                });

                xhr.addEventListener("error", event => {
                    this.showToastMessage('오류가 발생하여 배너 이미지 저장 요청이 전송되지 않았습니다.');
                    this.resetBannerImage();
                    this.closeLoadingWithMask();
                });
                xhr.send(new FormData(this.bannerImageForm));
                this.loadingWithMask();
            }

            fileReader.readAsDataURL(imgFile[0]);
        }
    }

    readProductImage(input) {
        let imgFile = input.files;

        if (imgFile.length >= this.MAX_PRODUCT_IMAGE_COUNT) {
            this.showToastMessage("상품 이미지는 최대 5개 까지 첨부 가능 합니다.");
            this.resetProductImage();
            return;
        }

        if (imgFile && imgFile[0]) {
            let fileForms = ['jpg', 'jpeg', 'png', 'gif', 'GIF'];
            let fileSize = 50 * 1024 * 1024;
            let fileExtension = input.value.slice(input.value.lastIndexOf(".") + 1)

            if (!fileForms.includes(fileExtension)) {
                this.showToastMessage("지정 된 이미지 파일만 업로드 가능합니다.");
                this.resetProductImage();
                return;
            } else if (imgFile[0].size >= fileSize) {
                this.showToastMessage('최대 파일 사이즈는 50MB 입니다.');
                this.resetProductImage();
                return;
            }
            const fileReader = new FileReader();

            fileReader.onload = (event) => {
                const xhr = new XMLHttpRequest();

                xhr.open("POST", "/register/images" + "?dest=temp");

                xhr.addEventListener("loadend", event => {
                    let status = event.target.status;
                    const responseJson = JSON.parse(event.target.responseText);
                    const responseJsonText = JSON.stringify(responseJson, null, 4);

                    if (status >= 400 && status <= 500) {
                        alert(`상품 이미지 저장 작업 중에 에러 발생!! \n\n error message: ${responseJsonText}`);
                        this.resetProductImage();
                    } else {
                        const imageListTemplate = document.querySelector("#product-image-list-template").innerHTML;
                        const template = Handlebars.compile(imageListTemplate);
                        const templateHTML = template({imageSrcList: responseJson});
                        this.productThumbnailImageUl.innerHTML += templateHTML;
                    }
                    this.closeLoadingWithMask();
                });

                xhr.addEventListener("error", event => {
                    this.showToastMessage('오류가 발생하여 상품 이미지 저장 요청이 전송되지 않았습니다.');
                    this.resetProductImage();
                    this.closeLoadingWithMask();
                });
                xhr.send(new FormData(this.productImageForm));
                this.loadingWithMask();
            }

            fileReader.readAsDataURL(imgFile[0]);
        }
    }

    resetProductImage() {
        // Assign buffer to file input
        let fileBuffer = new DataTransfer();
        this.productImageInput.files = fileBuffer.files; // <-- according to your file input reference
        this.productThumbnailImageUl.innerHTML = "";
    }

    resetBannerImage() {
        // Assign buffer to file input
        let fileBuffer = new DataTransfer();
        this.bannerImageInput.files = fileBuffer.files; // <-- according to your file input reference
        this.bannerThumbnailImg.src = "../image/default_thumbnail.gif"; // set default image
    }

    getProductImageForm() {
        return this.productImageForm;
    }

    getProductImageInput() {
        return this.productImageInput;
    }

    getBannerImageForm() {
        return this.bannerImageForm;
    }

    getBannerImageInput() {
        return this.bannerImageInput;
    }
}

/**
 * 배너 폼 컨트롤러
 */
class BannerFormController extends UtilController {
    constructor() {
        super();
        this.imageController = new ImageController();
        this.bannerForm = document.forms["banner_form"];
        this.registerBtn = document.getElementById("banner_register_btn");
        this.bannerExpirationDateForm = document.getElementById("banner_expiration_date");
        this.bannerLinkForm = document.getElementById("banner_link");
    }

    initBannerFormController() {
        this.setRegisterBtnClickListener();
    }

    setRegisterBtnClickListener() {
        this.registerBtn.addEventListener("click", () => {
            if (this.checkRegisterForm()) {
                this.showToastMessage("상품 정보를 올바르게 입력해주세요.");
                return;
            } else {
                this.submitRegisterForm();
            }
        });
    }

    checkRegisterForm() {
        return false;
    }

    submitRegisterForm() {
        const bannerImageInput = this.imageController.getBannerImageInput();
        const imgFile = bannerImageInput.files;

        if (imgFile && imgFile[0]) {
            const bannerImgXhr = new XMLHttpRequest();

            bannerImgXhr.open("POST", "/register/image" + "?dest=admin");

            bannerImgXhr.addEventListener("loadend", event => {
                let status = event.target.status;

                if (status >= 400 && status <= 500) {
                    const responseJson = JSON.parse(event.target.responseText);
                    const responseJsonText = JSON.stringify(responseJson, null, 4);
                    this.showToastMessage(`이미지 정보 저장 작업 중에 에러 발생!! \n\n error message: ${responseJsonText}`);
                }

                this.submitBannerForm(event.target.responseText);
            });

            bannerImgXhr.addEventListener("error", event => {
                this.showToastMessage('오류가 발생하여 상품 정보 요청이 전송되지 않았습니다.');
                this.resetBannerImage();
            });

            bannerImgXhr.send(new FormData(this.imageController.getBannerImageForm()));
        } else {
            this.submitBannerForm([]);
        }
    }

    submitBannerForm(bannerImgSrc) {
        const formData = new FormData(this.bannerForm);
        const xhr = new XMLHttpRequest();
        const params = `?imgSrc=${bannerImgSrc}&bannerLink=${this.bannerLinkForm.value}`;

        xhr.open("POST", "/register/bannerImage" + params);

        xhr.addEventListener("loadend", event => {
            let status = event.target.status;
            let responseJSON = JSON.parse(event.target.responseText);
            let responseText = JSON.stringify(responseJSON, null, 4);

            this.initValidationErrorMessage();

            if (status >= 400 && status <= 500) {
                this.writeValidationErrorMessage(responseJSON);
                alert(`배너 이미지 정보 저장 작업 중에 에러 발생!! \n\n error message: ${responseText}`);
            } else {
                this.showToastMessage('배너 이미지 정보 등록 완료');
                this.resetRegisterForm();
            }
            this.closeLoadingWithMask();
        });

        xhr.addEventListener("error", event => {
            this.showToastMessage('오류가 발생하여 배너 이미지 정보 요청이 전송되지 않았습니다.');
            this.closeLoadingWithMask();
        });

        xhr.send(formData);
        this.loadingWithMask();
    }

    writeValidationErrorMessage(responseJSON) {
        const errorList = responseJSON['errorList'];

        if (errorList !== undefined) {
            errorList.forEach(error => {
                const name = error['field'];
                const invalidData = error['invalidValue'];
                const message = error['message'];
                const validationErrMsg = `${message}`;

                switch (name) {
                }
            });
        }
    }

    initValidationErrorMessage() {
    }

    resetRegisterForm() {
        this.bannerExpirationDateForm.value = "";
        this.bannerLinkForm.value = "";
        this.imageController.resetBannerImage();
    }
}

/**
 * 상품 폼 컨트롤러
 * **/
class ProductFormController extends UtilController {
    constructor() {
        super();
        this.imageController = new ImageController();
        this.productForm = document.forms["product_form"];
        this.registerBtn = document.querySelector("#product_register_btn");
        this.productNameForm = document.querySelector("#product_name");
        this.productCategoryForm = document.querySelector("#product_category");
        this.productPriceForm = document.querySelector("#product_price");
        this.productDescriptionForm = document.querySelector("#product_description");
        this.shippingFeeForm = document.querySelector("#shipping_fee");
        this.productDiscountForm = document.querySelector("#product_discount");
        this.productCouponForm = document.querySelector("#product_coupon");
        this.productSellerForm = document.querySelector("#product_seller");
        this.productDeliveryForm = document.querySelector("#product_delivery");

        this.reloadButton = document.getElementById("product_reload_button");
    }

    initProductFormController() {
        this.sendCategoryFormReq(false);
        this.initReloadButton();
        this.setRegisterBtnClickListener();
    }

    initReloadButton() {
        this.reloadButton.addEventListener("click", () => {
            this.sendCategoryFormReq(true);
            this.showToastMessage("카테고리, 상품 정보 로드 완료");
        });
    }

    sendCategoryFormReq(reload) {
        // init product Category form
        const categoryXhr = new XMLHttpRequest();
        categoryXhr.open("GET", "/register/category");

        categoryXhr.addEventListener("loadend", event => {
            let status = event.target.status;

            if (status === 200) {
                let categoryList = JSON.parse(event.target.responseText);
                let productCategoryForm = this.productCategoryForm;

                if (reload === true) {
                    productCategoryForm.innerHTML = '';
                    productCategoryForm.add(new Option("카테고리 없음", Number.NaN));
                }

                for (let i = 0; i < categoryList.length; i++) {
                    productCategoryForm.add(new Option(categoryList[i]["name"], categoryList[i]["id"]));
                }
            }
        });
        categoryXhr.send();

        // init seller category form
        const sellerXhr = new XMLHttpRequest();
        sellerXhr.open("GET", "/register/seller")

        sellerXhr.addEventListener("loadend", event => {
            let status = event.target.status;

            if (status === 200) {
                let sellerList = JSON.parse(event.target.responseText);
                let productSellerForm = this.productSellerForm;

                if (reload === true) {
                    productSellerForm.innerHTML = '';
                    productSellerForm.add(new Option("추가 안함", Number.NaN));
                }

                for (let i = 0; i < sellerList.length; i++) {
                    productSellerForm.add(new Option(`${sellerList[i]["cname"]} - ${sellerList[i]["uname"]}`, sellerList[i]["id"]));
                }
            }
        });

        sellerXhr.send();
        // TODO init delivery category form
    }

    setRegisterBtnClickListener() {
        this.registerBtn.addEventListener("click", () => {
            if (this.checkRegisterForm()) {
                this.showToastMessage("상품 정보를 올바르게 입력해주세요.");
                return;
            } else {
                this.submitRegisterForm();
            }
        });
    }

    submitRegisterForm() {
        const productImageInput = this.imageController.getProductImageInput();
        const imgFile = productImageInput.files;

        if (imgFile && imgFile[0]) {
            const prodImgXhr = new XMLHttpRequest();

            prodImgXhr.open("POST", "/register/images" + "?dest=admin");

            prodImgXhr.addEventListener("loadend", event => {
                let status = event.target.status;
                const responseJson = JSON.parse(event.target.responseText);
                const responseJsonText = JSON.stringify(responseJson, null, 4);

                if (status >= 400 && status <= 500) {
                    this.showToastMessage(`이미지 정보 저장 작업 중에 에러 발생!! \n\n error message: ${responseJsonText}`);
                }
                this.submitProdForm(responseJson);
            });

            prodImgXhr.addEventListener("error", event => {
                this.showToastMessage('오류가 발생하여 상품 정보 요청이 전송되지 않았습니다.');
                this.resetProductImage();
            });

            prodImgXhr.send(new FormData(this.imageController.getProductImageForm()));
        } else {
            this.submitProdForm([]);
        }
    }

    submitProdForm(prodImgSrcList) {
        const formData = new FormData(this.productForm);
        const xhr = new XMLHttpRequest();
        const params = `?categoryId=${this.productCategoryForm.value}&shippingFee=${this.shippingFeeForm.value}&sellerId=${this.productSellerForm.value}&imgSrcList=${prodImgSrcList}`;

        xhr.open("POST", "/register/product" + params);

        xhr.addEventListener("loadend", event => {
            let status = event.target.status;
            let responseJSON = JSON.parse(event.target.responseText);
            let responseText = JSON.stringify(responseJSON, null, 4);

            this.initValidationErrorMessage();

            if (status >= 400 && status <= 500) {
                this.writeValidationErrorMessage(responseJSON);
                alert(`상품 정보 저장 작업 중에 에러 발생!! \n\n error message: ${responseText}`);
            } else {
                this.showToastMessage('상품 정보 등록 완료');
                this.resetRegisterForm();
            }
            this.closeLoadingWithMask();
        });

        xhr.addEventListener("error", event => {
            this.showToastMessage('오류가 발생하여 상품 정보 요청이 전송되지 않았습니다.');
            this.closeLoadingWithMask();
        });

        xhr.send(formData);
        this.loadingWithMask();
    }

    writeValidationErrorMessage(responseJSON) {
        const errorList = responseJSON['errorList'];

        if (errorList !== undefined) {
            errorList.forEach(error => {
                const name = error['field'];
                const invalidData = error['invalidValue'];
                const message = error['message'];
                const validationErrMsg = `${message}`;

                switch (name) {
                    case 'name' :
                        const nameValidation = document.querySelector("#name_validation");
                        nameValidation.textContent = validationErrMsg;
                        break;

                    case 'description':
                        const descValidation = document.querySelector("#description_validation");
                        descValidation.textContent = validationErrMsg;
                        break;

                    case 'price':
                        const priceValidation = document.querySelector("#price_validation");
                        priceValidation.textContent = validationErrMsg;
                        break;

                    case 'shippingFee':
                        const shippingValidation = document.querySelector("#shipping_validation");
                        shippingValidation.textContent = validationErrMsg;
                        break;

                    case 'discount':
                        const discountValidation = document.querySelector("#discount_validation");
                        discountValidation.textContent = validationErrMsg;
                        break;
                }
            });
        }
    }

    initValidationErrorMessage() {
        const nameValidation = document.querySelector("#name_validation")
        nameValidation.textContent = ``;

        const descValidation = document.querySelector("#description_validation");
        descValidation.textContent = ``;

        const priceValidation = document.querySelector("#price_validation");
        priceValidation.textContent = ``;

        const shippingValidation = document.querySelector("#shipping_validation");
        shippingValidation.textContent = ``;

        const discountValidation = document.querySelector("#discount_validation");
        discountValidation.textContent = ``;
    }

    checkRegisterForm() {
        if (
            !this.productNameForm.value
            || (!this.productCategoryForm.value || this.productCategoryForm.value === this.productCategoryForm.options[0].value)
            || (!this.productSellerForm.value || this.productSellerForm.value === this.productSellerForm.options[0].value)
            || (!this.productPriceForm.value || Number.isNaN(parseInt(this.productPriceForm.value))
                || Number(this.productPriceForm.value) > Number.MAX_SAFE_INTEGER)
            || (!this.shippingFeeForm.value || Number.isNaN(parseInt(this.shippingFeeForm.value))
                || Number(this.shippingFeeForm.value) > 10000)
            || (!this.productDiscountForm.value || Number.isNaN(parseInt(this.productDiscountForm.value))
                || Number(this.productDiscountForm.value) > 100)
            || !this.productDeliveryForm.value
        ) {
            return true;
        } else {
            return false;
        }
    }

    resetRegisterForm() {
        this.productNameForm.value = "";
        this.productCategoryForm[0].selected = true;
        this.productPriceForm.value = "";
        this.productDiscountForm.value = "";
        this.productDescriptionForm.value = "";
        this.shippingFeeForm[0].selected = true;
        this.productSellerForm[0].selected = true;
        this.productDeliveryForm[0].selected = true;
        this.imageController.resetProductImage();
    }
}

/**
 * 카테고리 폼 컨트롤러
 */
class CategoryFormController extends UtilController {
    constructor() {
        super();
        this.categoryForm = document.forms["category_form"];
        this.registerBtn = document.querySelector("#category_register_btn");
        this.categoryNameForm = document.querySelector("#category_name");
    }

    initCategoryFormController() {
        this.setRegisterBtnClickListener();
    }

    setRegisterBtnClickListener() {
        this.registerBtn.addEventListener("click", () => {
            if (this.checkRegisterForm()) {
                this.showToastMessage('판매자 정보를 올바르게 입력해주세요.');
                return;
            } else {
                this.submitRegisterForm();
            }
        });
    }

    submitRegisterForm() {
        const formData = new FormData(this.categoryForm);
        const xhr = new XMLHttpRequest();

        xhr.open("POST", "/register/category");

        xhr.addEventListener("loadend", event => {
            let status = event.target.status;
            let responseJSON = JSON.parse(event.target.responseText);
            let responseText = JSON.stringify(responseJSON, null, 4);

            this.initValidationErrorMessage();

            if (status >= 400 && status <= 500) {
                this.writeValidationErrorMessage(responseJSON);
                alert(`카테고리 정보 저장 작업 중에 에러 발생!! \n\n error message: ${responseText}`);
            } else {
                this.showToastMessage('카테고리 정보 등록 완료');
                this.resetRegisterForm();
            }
            this.closeLoadingWithMask();
        });

        xhr.addEventListener("error", event => {
            this.showToastMessage('오류가 발생하여 카테고리 정보 요청이 전송되지 않았습니다.');
            this.closeLoadingWithMask();
        });

        xhr.send(formData);
        this.loadingWithMask();
    }

    writeValidationErrorMessage(responseJSON) {
        const errorList = responseJSON['errorList'];

        if (errorList !== undefined) {
            errorList.forEach(error => {
                const name = error['field'];
                const invalidData = error['invalidValue'];
                const message = error['message'];
                const validationErrMsg = `${message}`;

                switch (name) {
                    case 'name' :
                        const categoryNameValidation = document.querySelector("#category_name_validation");
                        categoryNameValidation.textContent = validationErrMsg;
                        break;
                }
            });
        }
    }

    initValidationErrorMessage() {
        const categoryNameValidation = document.querySelector("#category_name_validation");
        categoryNameValidation.textContent = ``;
    }

    checkRegisterForm() {
        if (
            !this.categoryNameForm.value) {
            return true;
        } else {
            return false;
        }
    }

    resetRegisterForm() {
        this.categoryNameForm.value = "";
    }
}

/**
 * 판매자 폼 컨트롤러
 * **/
class SellerFormController extends UtilController {
    constructor() {
        super();
        this.sellerForm = document.forms["seller_form"];
        this.registerBtn = document.querySelector("#seller_register_btn");
        this.uNameForm = document.querySelector("#seller_name");
        this.cNameForm = document.querySelector("#company_name");
        this.businessTypeForm = document.querySelector("#business_type");
        this.addressForm = document.querySelector("#seller_address");
        this.emailForm = document.querySelector("#seller_email");
        this.homepageForm = document.querySelector("#seller_homepage");
        this.callForm = document.querySelector("#seller_call");
        this.businessNumberForm = document.querySelector("#seller_business_number");
    }

    initSellerFormController() {
        this.setRegisterBtnClickListener();
    }

    setRegisterBtnClickListener() {
        this.registerBtn.addEventListener("click", () => {
            if (this.checkRegisterForm()) {
                this.showToastMessage('판매자 정보를 올바르게 입력해주세요.');
                return;
            } else {
                this.submitRegisterForm();
            }
        });
    }

    submitRegisterForm() {
        const formData = new FormData(this.sellerForm);
        const xhr = new XMLHttpRequest();
        const param = `?businessType=${formData.get("business_type")}`;

        xhr.open("POST", "/register/seller" + param);

        xhr.addEventListener("loadend", event => {
            let status = event.target.status;
            let responseJSON = JSON.parse(event.target.responseText);
            let responseText = JSON.stringify(responseJSON, null, 4);

            this.initValidationErrorMessage();

            if (status >= 400 && status <= 500) {
                this.writeValidationErrorMessage(responseJSON);
                alert(`판매자 정보 저장 작업 중에 에러 발생!! \n\n error message: ${responseText}`);
            } else {
                this.showToastMessage('판매자 정보 등록 완료');
                this.resetRegisterForm();
            }
            this.closeLoadingWithMask();
        });

        xhr.addEventListener("error", event => {
            this.showToastMessage('오류가 발생하여 판매자 정보 요청이 전송되지 않았습니다.');
            this.closeLoadingWithMask();
        });

        xhr.send(formData);
        this.loadingWithMask();
    }

    writeValidationErrorMessage(responseJSON) {
        const errorList = responseJSON['errorList'];

        if (errorList !== undefined) {
            errorList.forEach(error => {
                const name = error['field'];
                const invalidData = error['invalidValue'];
                const message = error['message'];
                const validationErrMsg = `${message}`;

                switch (name) {
                    case 'cName' :
                        const cNameValidation = document.querySelector("#cname_validation");
                        cNameValidation.textContent = validationErrMsg;
                        break;

                    case 'uName':
                        const uNameValidation = document.querySelector("#uname_validation");
                        uNameValidation.textContent = validationErrMsg;
                        break;

                    case 'businessType':
                        const businessTypeValidation = document.querySelector("#business_type_validation");
                        businessTypeValidation.textContent = validationErrMsg;
                        break;

                    case 'address':
                        const addressValidation = document.querySelector("#address_validation");
                        addressValidation.textContent = validationErrMsg;
                        break;

                    case 'email':
                        const emailValidation = document.querySelector("#email_validation");
                        emailValidation.textContent = validationErrMsg;
                        break;

                    case 'homepage':
                        const homepageValidation = document.querySelector("#seller_homepage_validation");
                        homepageValidation.textContent = validationErrMsg;
                        break;

                    case 'callNumber':
                        const callNumberValidation = document.querySelector("#seller_call_validation");
                        callNumberValidation.textContent = validationErrMsg;
                        break;

                    case 'businessNumber':
                        const businessNumberValidation = document.querySelector("#seller_business_number_validation");
                        businessNumberValidation.textContent = validationErrMsg;
                        break;
                }
            });
        }
    }

    initValidationErrorMessage() {
        const cNameValidation = document.querySelector("#cname_validation");
        cNameValidation.textContent = ``;

        const uNameValidation = document.querySelector("#uname_validation");
        uNameValidation.textContent = ``;

        const businessTypeValidation = document.querySelector("#business_type_validation");
        businessTypeValidation.textContent = ``;

        const addressValidation = document.querySelector("#address_validation");
        addressValidation.textContent = ``;

        const emailValidation = document.querySelector("#email_validation");
        emailValidation.textContent = ``;

        const homepageValidation = document.querySelector("#seller_homepage_validation");
        homepageValidation.textContent = ``;

        const callNumberValidation = document.querySelector("#seller_call_validation");
        callNumberValidation.textContent = ``;

        const businessNumberValidation = document.querySelector("#seller_business_number_validation");
        businessNumberValidation.textContent = ``;
    }

    checkRegisterForm() {
        if (
            !this.uNameForm.value
            || !this.cNameForm.value
            || !this.businessTypeForm.value
            || !this.addressForm.value
            || (this.emailForm.value == '' || !this.emailRegExp.test(this.emailForm.value))
            || (this.homepageForm.value == '' || !this.urlRegExp.test(this.homepageForm.value))
            || (this.callForm.value == '' || !this.callRegExp.test(this.callForm.value))
            || (!this.businessNumberForm.value || Number.isNaN(parseInt(this.businessNumberForm.value)))) {
            return true;
        } else {
            return false;
        }
    }

    resetRegisterForm() {
        this.uNameForm.value = "";
        this.cNameForm.value = "";
        this.businessTypeForm[0].selected = true;
        this.addressForm.value = "";
        this.emailForm.value = "";
        this.addressForm.value = "";
        this.homepageForm.value = "";
        this.callForm.value = "";
        this.businessNumberForm.value = "";
    }
}

/**
 * 쿠폰 폼 컨트롤러
 */
class CouponFormController extends UtilController {
    constructor() {
        super();
        this.couponForm = document.forms["coupon_form"];
        this.registerBtn = document.querySelector("#coupon_register_btn");
        this.couponNameForm = document.querySelector("#coupon_name");
        this.couponCategoryForm = document.querySelector("#coupon_category");
        this.couponDescriptionForm = document.querySelector("#coupon_description");
        this.couponPriceLimitForm = document.querySelector("#coupon_price_limit");
        this.couponMaxDiscountPriceForm = document.querySelector("#coupon_max_discount_price");
        this.couponDiscountPercentForm = document.querySelector("#coupon_discount_percent");
    }

    initCouponFormController() {
        const categoryXhr = new XMLHttpRequest();
        categoryXhr.open("GET", "/register/category");

        categoryXhr.addEventListener("loadend", event => {
            let status = event.target.status;

            if (status === 200) {
                let categoryList = JSON.parse(event.target.responseText);
                let couponCategoryForm = this.couponCategoryForm;

                for (let i = 0; i < categoryList.length; i++) {
                    couponCategoryForm.add(new Option(categoryList[i]["name"], categoryList[i]["id"]));
                }
            }
        });

        categoryXhr.send();

        this.setRegisterBtnClickListener();
    }

    setRegisterBtnClickListener() {
        this.registerBtn.addEventListener("click", () => {
            if (this.checkRegisterForm()) {
                this.showToastMessage('쿠폰 정보를 올바르게 입력해주세요.');
                return;
            } else {
                this.submitRegisterForm();
            }
        });
    }

    submitRegisterForm() {
        const formData = new FormData(this.couponForm);
        const xhr = new XMLHttpRequest();

        xhr.open("POST", "/register/coupon");

        xhr.addEventListener("loadend", event => {
            let status = event.target.status;
            let responseJSON = JSON.parse(event.target.responseText);
            let responseText = JSON.stringify(responseJSON, null, 4);

            this.initValidationErrorMessage();

            if (status >= 400 && status <= 500) {
                this.writeValidationErrorMessage(responseJSON);
                alert(`쿠폰 정보 저장 작업 중에 에러 발생!! \n\n error message: ${responseText}`);
            } else {
                this.showToastMessage('쿠폰 정보 등록 완료');
                this.resetRegisterForm();
            }
            this.closeLoadingWithMask();
        });

        xhr.addEventListener("error", event => {
            this.showToastMessage('오류가 발생하여 쿠폰 정보 요청이 전송되지 않았습니다.');
            this.closeLoadingWithMask();
        });

        xhr.send(formData);
        this.loadingWithMask();
    }

    writeValidationErrorMessage(responseJSON) {
        const errorList = responseJSON['errorList'];

        if (errorList !== undefined) {
            errorList.forEach(error => {
                const name = error['field'];
                const invalidData = error['invalidValue'];
                const message = error['message'];
                const validationErrMsg = `${message}`;

                switch (name) {
                    case 'name' :
                        const couponNameValidation = document.querySelector("#coupon_name_validation");
                        couponNameValidation.textContent = validationErrMsg;
                        break;

                    case 'description':
                        const couponDescriptionValidation = document.querySelector("#coupon_description_validation");
                        couponDescriptionValidation.textContent = validationErrMsg;
                        break;

                    case 'priceLimit':
                        const couponPriceLimitValidation = document.querySelector("#coupon_price_limit_validation");
                        couponPriceLimitValidation.textContent = validationErrMsg;
                        break;

                    case 'maxDiscountPrice':
                        const couponMaxDiscountPriceValidation = document.querySelector("#coupon_max_discount_price_validation");
                        couponMaxDiscountPriceValidation.textContent = validationErrMsg;
                        break;

                    case 'discountPercent':
                        const couponDiscountPercentValidation = document.querySelector("#coupon_discount_percent_validation");
                        couponDiscountPercentValidation.textContent = validationErrMsg;
                        break;
                }
            });
        }
    }

    initValidationErrorMessage() {
        const couponNameValidation = document.querySelector("#coupon_name_validation");
        couponNameValidation.textContent = ``;

        const couponDescriptionValidation = document.querySelector("#coupon_description_validation");
        couponDescriptionValidation.textContent = ``;

        const couponPriceLimitValidation = document.querySelector("#coupon_price_limit_validation");
        couponPriceLimitValidation.textContent = ``;

        const couponMaxDiscountPriceValidation = document.querySelector("#coupon_max_discount_price_validation");
        couponMaxDiscountPriceValidation.textContent = ``;

        const couponDiscountPercentValidation = document.querySelector("#coupon_discount_percent_validation");
        couponDiscountPercentValidation.textContent = ``;
    }

    checkRegisterForm() {
        if (
            !this.couponNameForm.value
            || !this.couponCategoryForm.value
            || !this.couponDescriptionForm.value
            || (!this.couponPriceLimitForm.value || Number.isNaN(parseInt(this.couponPriceLimitForm.value)))
            || (!this.couponMaxDiscountPriceForm.value || Number.isNaN(parseInt(this.couponMaxDiscountPriceForm.value)))
            || (!this.couponDiscountPercentForm.value || Number.isNaN(parseInt(this.couponDiscountPercentForm.value)))
        ) {
            return true;
        } else {
            return false;
        }
    }

    resetRegisterForm() {
        this.couponNameForm.value = "";
        this.couponCategoryForm[0].selected = true;
        this.couponDescriptionForm.value = "";
        this.couponPriceLimitForm.value = "";
        this.couponMaxDiscountPriceForm.value = "";
        this.couponDiscountPercentForm.value = "";
    }
}

/**
 * JmShop Form 전체 컨트롤러
 * **/
class JmShopFormController {
    constructor() {
        this.imageController = new ImageController();
        this.bannerImageFormController = new BannerFormController();
        this.productFormController = new ProductFormController();
        this.sellerFormController = new SellerFormController();
        this.couponFormController = new CouponFormController();
        this.categoryFormController = new CategoryFormController();
    }

    initJmShopFormController() {
        this.imageController.initImageController();
        this.bannerImageFormController.initBannerFormController();
        this.productFormController.initProductFormController();
        this.sellerFormController.initSellerFormController();
        this.couponFormController.initCouponFormController();
        this.categoryFormController.initCategoryFormController();
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const jmShopFormController = new JmShopFormController();
    // 추후에 로드 시에, 서버에서 전달받은 데이터 로드 메서드 호출
    jmShopFormController.initJmShopFormController();
});