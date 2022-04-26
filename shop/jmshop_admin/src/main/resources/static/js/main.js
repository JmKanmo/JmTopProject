class UrlParser {
    constructor() {
        this.sch = location.search;
    }
}

class ImageController {
    constructor() {
        this.productImageInput = document.getElementById("product_image");
        this.thumbnailImg = document.querySelector(".thumbnail_img");
        this.resetImageBtn = document.querySelector(".reset_img_btn");
    }

    initImageController() {
        this.updateThumbnailImage();
        this.setResetImgBtnClickListener();
    }

    updateThumbnailImage() {
        const productImageInput = this.productImageInput;
        productImageInput.addEventListener("change", event => {
            this.readImage(event.target);
        });
    }

    setResetImgBtnClickListener() {
        const resetImageBtn = this.resetImageBtn;
        resetImageBtn.addEventListener("click", () => {
            if (confirm("이미지를 초기화 하시겠습니까?")) {
                this.resetImage();
            }
        });
    }

    readImage(input) {
        let imgFile = input.files;

        if (imgFile && imgFile[0]) {
            let fileForms = ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'pdf'];
            let fileSize = 50 * 1024 * 1024;
            let fileExtension = input.value.slice(input.value.lastIndexOf(".") + 1)

            if (!fileForms.includes(fileExtension)) {
                alert("지정 된 이미지 파일만 업로드 가능합니다.");
                this.resetImage();
                return;
            } else if (imgFile[0].size >= fileSize) {
                alert('최대 파일 사이즈는 50MB 입니다.');
                this.resetImage();
                return;
            }
            const fileReader = new FileReader();

            fileReader.onload = (event) => {
                this.thumbnailImg.src = event.target.result;
            }

            fileReader.readAsDataURL(input.files[0]);
        }
    }

    resetImage() {
        // Assign buffer to file input
        let fileBuffer = new DataTransfer();
        this.productImageInput.files = fileBuffer.files; // <-- according to your file input reference
        this.thumbnailImg.src = "../image/default_thumbnail.gif"; // set default image
    }
}

class ProductFormController {
    constructor() {
        this.imageController = new ImageController();
        this.registerForm = document.forms["register_form"];
        this.registerBtn = document.querySelector(".register_btn");
        this.productNameForm = document.querySelector("#product_name");
        this.productCategoryForm = document.querySelector("#product_category");
        this.productPriceForm = document.querySelector("#product_price");
        this.productDescriptionForm = document.querySelector("#product_description");
        this.productTagForm = document.querySelector("#product_tag");
        this.shippingFeeForm = document.querySelector("#shipping_fee");
        this.productDiscountForm = document.querySelector("#product_discount");
        this.productCouponForm = document.querySelector("#product_coupon");
        this.productDeliveryForm = document.querySelector("#product_delivery");
    }

    initProductFormController() {
        this.setRegisterBtnClickListener();
    }

    setRegisterBtnClickListener() {
        this.registerBtn.addEventListener("click", () => {
            if (this.checkRegisterForm()) {
                alert('상품 정보를 올바르게 입력해주세요.');
                return;
            } else {
                this.submitRegisterForm();
            }
        });
    }

    submitRegisterForm() {
        const formData = new FormData(this.registerForm);
        const xhr = new XMLHttpRequest();

        xhr.open("POST", "/register/product");

        xhr.addEventListener("loadend", event => {
            let status = event.target.status;
            let responseJSON = JSON.parse(event.target.responseText);
            let responseText = JSON.stringify(responseJSON, null, 4);

            this.initValidationErrorMessage();

            if (status >= 400 && status <= 500) {
                this.writeValidationErrorMessage(responseJSON);
                alert(`!!상품 정보 저장 작업 중에 에러 발생!! \n\n error message: ${responseText}`);
            } else {
                alert('상품 정보 등록 완료');
                this.resetRegisterForm();
            }
            this.closeLoadingWithMask();
        });

        xhr.addEventListener("error", event => {
            alert('오류가 발생하여 상품 정보 요청이 전송되지 않았습니다.');
            this.closeLoadingWithMask();
        });

        xhr.send(formData);
        this.loadingWithMask();
    }

    writeValidationErrorMessage(responseJSON) {
        const errorList = responseJSON['errorList'];

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

                case 'tag':
                    const tagValidation = document.querySelector("#tag_validation");
                    tagValidation.textContent = validationErrMsg;
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

    initValidationErrorMessage() {
        const nameValidation = document.querySelector("#name_validation")
        nameValidation.textContent = ``;

        const descValidation = document.querySelector("#description_validation");
        descValidation.textContent = ``;

        const tagValidation = document.querySelector("#tag_validation");
        tagValidation.textContent = ``;

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
            || !this.productCategoryForm.value
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
        this.productCategoryForm.value = this.productCategoryForm.options[0].text;
        this.productPriceForm.value = "";
        this.productDiscountForm.value = "";
        this.productDescriptionForm.value = "";
        this.productTagForm.value = "";
        this.shippingFeeForm.value = this.shippingFeeForm.options[0].text;
        this.productCouponForm.value = this.productCouponForm.options[0].text;
        this.productDeliveryForm.value = this.productDeliveryForm.options[0].text;
        this.imageController.resetImage();
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
}

class JmShopFormController {
    constructor() {
        this.imageController = new ImageController();
        this.productFormController = new ProductFormController();
    }

    initJmShopFormController() {
        this.imageController.initImageController();
        this.productFormController.initProductFormController();
    }
}

// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    const jmShopFormController = new JmShopFormController();
    // 추후에 로드 시에, 서버에서 전달받은 데이터 로드 메서드 호출
    jmShopFormController.initJmShopFormController();
});