class ProductFormController {
    constructor() {
        this.productImageInput = document.getElementById("product_image");
        this.thumbnailImg = document.querySelector(".thumbnail_img");
    }

    initProductFormController() {
        this.updateThumbnailImage();
    }

    updateThumbnailImage() {
        const productImageInput = this.productImageInput;
        productImageInput.addEventListener("change", event => {
            this.readImage(event.target);
        })
    }

    readImage(input) {
        let imgFile = input.files;

        if (imgFile && imgFile[0]) {
            let fileForms = ['jpg', 'jpeg', 'png', 'gif', 'bmp', 'pdf'];
            let fileSize = 50 * 1024 * 1024;
            let fileExtension = input.value.slice(input.value.lastIndexOf(".") + 1)

            if (!fileForms.includes(fileExtension)) {
                alert("지정 된 이미지 파일만 업로드 가능합니다.");
                return;
            } else if (imgFile[0].size >= fileSize) {
                alert('최대 파일 사이즈는 50MB 입니다.');
                return;
            }
            const fileReader = new FileReader();

            fileReader.onload = (event) => {
                this.thumbnailImg.src = event.target.result;
            }

            fileReader.readAsDataURL(input.files[0]);
        }

    }
}

class JmShopFormController {
    constructor() {
        this.productFormController = new ProductFormController();
    }

    initJmShopFormController() {
        this.productFormController.initProductFormController();
    }
}


// Execute all functions
document.addEventListener("DOMContentLoaded", function () {
    const jmShopFormController = new JmShopFormController();
    jmShopFormController.initJmShopFormController();
});