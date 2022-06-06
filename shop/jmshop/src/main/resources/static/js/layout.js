/**
 * 이미지 기능 관련 컨트롤러
 * **/
class ImageController {
    constructor() {
        this.productImageInput = document.getElementById("product_image");
        this.productThumbnailImg = document.querySelector(".thumbnail_img");
        this.productResetImageBtn = document.querySelector(".reset_img_btn");
    }

    initProductImageController() {
        this.updateProductThumbnailImage();
        this.setProductResetImgBtnClickListener();
    }

    updateProductThumbnailImage() {
        const productImageInput = this.productImageInput;
        productImageInput.addEventListener("change", event => {
            this.readProductImage(event.target);
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

    readProductImage(input) {
        let imgFile = input.files;

        if (imgFile && imgFile[0]) {
            let fileForms = ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'pdf'];
            let fileSize = 50 * 1024 * 1024;
            let fileExtension = input.value.slice(input.value.lastIndexOf(".") + 1)

            if (!fileForms.includes(fileExtension)) {
                alert("지정 된 이미지 파일만 업로드 가능합니다.");
                this.resetProductImage();
                return;
            } else if (imgFile[0].size >= fileSize) {
                alert('최대 파일 사이즈는 50MB 입니다.');
                this.resetProductImage();
                return;
            }
            const fileReader = new FileReader();

            fileReader.onload = (event) => {
                this.productThumbnailImg.src = event.target.result;
            }

            fileReader.readAsDataURL(input.files[0]);
        }
    }

    resetProductImage() {
        // Assign buffer to file input
        let fileBuffer = new DataTransfer();
        this.productImageInput.files = fileBuffer.files; // <-- according to your file input reference
        this.productThumbnailImg.src = "../image/default_thumbnail.gif"; // set default image
    }
}

/**
 * 상품 폼 컨트롤러
 * **/
class ProductFormController {
    constructor() {
        this.imageController = new ImageController();
        this.productForm = document.forms["product_form"];
        this.registerBtn = document.querySelector("#product_register_btn");
    }

    initProductFormController() {
        this.setRegisterBtnClickListener();
    }

    submitRegisterForm() {
        const formData = new FormData(this.productForm);
        const xhr = new XMLHttpRequest();
        formData.append("path", "/home/vsftpd");
        formData.append('imageFile', this.imageController.productImageInput.files[0], 'jmshop_img');
        let result = "";

        const url = "ftp://127.0.0.1:21";
        const settings = {
            "async": false,
            "crossDomain": true,
            "url": url,
            "method": "POST",
            "headers": {"cache-control": "no-cache",},
            "processData": false,
            "contentType": false,
            "mimeType": "multipart/form-data",
            "data": formData
        }

        $.ajax(settings).done(function (response) {
            console.log(response);
            result = JSON.parse(response);
        });
    }

    setRegisterBtnClickListener() {
        this.registerBtn.addEventListener("click", () => {
            this.submitRegisterForm();
        });
    }
}


// Execute all functions
document.addEventListener("DOMContentLoaded", () => {
    // 추후에 로드 시에, 서버에서 전달받은 데이터 로드 메서드 호출
    const prodCtr = new ProductFormController();
    prodCtr.initProductFormController();
});