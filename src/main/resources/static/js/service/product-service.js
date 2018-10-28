angular.module('ngBudgetCalc')
    .service('ProductService', function (ProductFactory, MemberService, RestService, Event, ChartService, CommonDataService) {
        let products = [];

        const today = dateFns.format(new Date(), 'YYYY-MM-DD');

        let loadProductsCallback = function (response) {
            return RestService.fetchData(() => {
                products = response.data;
            }, Event.PRODUCTS_CHANGED);
        };

        let saveProductCallback = function (response) {
            return RestService.fetchData(() => {
                products.push(response.data);
            }, Event.PRODUCTS_CHANGED);
        };

        let updateProductCallback = function (response) {
            return RestService.fetchData(() => {
                products = products.map(prod => getRightIfEquals(prod, response.data));
            }, Event.PRODUCTS_CHANGED);
        };

        let deleteProductCallback = function (response) {
            return RestService.fetchData(() => {
                products = products.filter(prod => prod.id !== response.data);
            }, Event.PRODUCTS_CHANGED)
        };

        let updateProductsByCategoryCallback = function (response) {
            return RestService.fetchData(() => {
                products = response.data;
            }, Event.PRODUCTS_CHANGED);
        };

        this.getAllProducts = () => products;
        this.getTodaysMemberProducts = () => products.filter(prod => prod.creditor === MemberService.getMember().name && prod.creationDate === today);
        this.getProductsByCategory = () => {
            let productsByCategory = products.groupBy('category');
            CommonDataService.getProductsCategories().map(cat => cat.name).forEach(cat => {
                if (productsByCategory[cat] === undefined) {
                    productsByCategory[cat] = [];
                }
            });
            return productsByCategory;
        };

        this.loadProducts = ProductFactory.getProducts(loadProductsCallback);
        this.saveProduct = ProductFactory.saveProduct(saveProductCallback);
        this.updateProduct = ProductFactory.updateProduct(updateProductCallback);
        this.deleteProduct = ProductFactory.deleteProduct(deleteProductCallback);
        this.updateProductsCategory = ProductFactory.updateProductsCategory(updateProductsByCategoryCallback);

        this.clearData = () => {
            products = [];
        }
    });