class MenuCategoryController {
    constructor() {
        this.menuCategoryButton = document.getElementById("menuBtn");
        this.menuCategorList = document.getElementById("menuCategoryList");
        this.menuCategoryFlag = false;
    }

    initMenuCategoryEventHandler() {
        this.requestMenuCategoryHttp();
        this.menuCategoryEventHandler();
    }

    menuCategoryEventHandler() {
        this.menuCategoryButton.addEventListener("click", evt => {
            if (this.menuCategoryFlag === false) {
                this.menuCategorList.style.display = 'block';
                this.menuCategoryFlag = true;
            } else {
                this.menuCategoryFlag = false;
                this.menuCategorList.style.display = 'none';
            }
        });
    }

    requestMenuCategoryHttp() {
        this.fetchMenuCategory("/category/menu").then(categoryData => {
            if (categoryData !== undefined) {
                const menuCategoryListTemplate = document.querySelector("#menu-category-list-template").innerHTML;
                const template = Handlebars.compile(menuCategoryListTemplate);
                const templateHTML = template({menuCategoryList: categoryData});
                this.menuCategorList.innerHTML += templateHTML;
            }
        });
    }

    async fetchMenuCategory(url) {
        try {
            const response = await fetch(url, {method: 'GET'});
            const responseJson = await response.json();
            return responseJson;
        } catch (error) {
            console.log("[MenuCategoryController:fetchMenuCategory] error =>", err);
            return undefined;
        }
    }
}

class HeaderController {
    constructor() {
        this.menuCategoryController = new MenuCategoryController();
    }

    initHeaderController() {
        this.menuCategoryController.initMenuCategoryEventHandler();
    }
}


document.addEventListener("DOMContentLoaded", () => {
    // 추후에 로드 시에, 서버에서 전달받은 데이터 로드 메서드 호출
    const headerController = new HeaderController();
    headerController.initHeaderController();
});