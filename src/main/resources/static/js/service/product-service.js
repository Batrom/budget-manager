angular.module('ngBudgetCalc')
    .service('productService', function (productFactory, memberService, restService, event, chartService) {
        let products = [];
        let productsByCategory = [];

        const today = dateFns.format(new Date(), 'YYYY-MM-DD');

        let loadProductsSuccess = function (response) {
            return restService.fetchData(() => {
                products = response.data;
            }, event.PRODUCTS_CHANGED);
        };

        let saveProductSuccess = function (response) {
            return restService.fetchData(() => {
                products.push(response.data);
            }, event.PRODUCTS_CHANGED);
        };

        let updateProductSuccess = function (response) {
            return restService.fetchData(() => {
                products = products.map(prod => getRightIfEquals(prod, response.data));
            }, event.PRODUCTS_CHANGED);
        };

        let deleteProductSuccess = function (response) {
            return restService.fetchData(() => {
                products = products.filter(prod => prod.id !== response.data.id);
            }, event.PRODUCTS_CHANGED)
        };

        let loadProductsByCategorySuccess = function (response) {
            return restService.fetchData(() => productsByCategory = response.data, event.GET_PRODUCTS_BY_CATEGORY);
        };

        let updateProductsByCategorySuccess = function (response) {
            return restService.fetchData(() => chartService.loadChartData(memberService.getMember().name));
        };

        this.getAllProducts = () => products;
        this.getMemberProducts = () => products.filter(prod => prod.creditor === memberService.getMember().name);
        this.getTodaysMemberProducts = () => this.getMemberProducts().filter(prod => prod.creationDate === today);
        this.getProductsByCategory = () => productsByCategory;

        this.loadProducts = productFactory.getProducts(loadProductsSuccess);
        this.saveProduct = productFactory.saveProduct(saveProductSuccess);
        this.updateProduct = productFactory.updateProduct(updateProductSuccess);
        this.deleteProduct = productFactory.deleteProduct(deleteProductSuccess);
        this.updateProductsCategory = productFactory.updateProductsCategory(updateProductsByCategorySuccess);
        this.loadProductsByCategory = productFactory.getProductsByCategory(loadProductsByCategorySuccess);
    });