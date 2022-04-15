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
                this.resetRegisterForm();
            }
        });
    }

    submitRegisterForm() {
        const formData = new FormData(this.registerForm);
        const xhr = new XMLHttpRequest();

        xhr.open("POST", "/register/product");

        xhr.addEventListener("load", event => {
            alert('상품 정보 등록 완료');
        });

        xhr.addEventListener("error", event => {
            alert('상품 정보 등록 중에 문제 발생');
        });

        xhr.send(formData);
    }

    checkRegisterForm() {
        if (
            !this.productNameForm.value
            || !this.productCategoryForm.value
            || (!this.productPriceForm.value || Number.isNaN(parseInt(this.productPriceForm.value)))
            || (!this.shippingFeeForm.value || Number.isNaN(parseInt(this.shippingFeeForm.value)))
            || (!this.productDiscountForm.value || Number.isNaN(parseInt(this.productDiscountForm.value)))
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
        this.productDescriptionForm.value = "";
        this.productTagForm.value = "";
        this.shippingFeeForm.value = this.shippingFeeForm.options[0].text;
        this.productCouponForm.value = this.productCouponForm.options[0].text;
        this.productDeliveryForm.value = this.productDeliveryForm.options[0].text;
        this.imageController.resetImage();
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