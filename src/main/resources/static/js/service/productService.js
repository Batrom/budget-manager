angular.module('ngBudgetCalc')
    .service('productService', function (productFactory, $rootScope, debtService) {
        let products = [];
        let userProducts = [];
        let todaysUserProducts = [];

        const today = dateFns.format(new Date(), 'YYYY-MM-DD');

        this.getProducts = function () {
            return products;
        };

        this.getUserProducts = function () {
            return userProducts;
        };

        this.getTodaysUserProducts = function () {
            return todaysUserProducts;
        };

        this.loadProducts = function () {
            productFactory.getProducts()
                .then(
                    function (response) {
                        fetchLoadProductsResponse(response);
                        debtService.loadDebts($rootScope.loggedUser);
                    },
                    function (error) {
                        console.error(error);
                    });
        };

        this.saveProduct = function (product) {
            productFactory.saveProduct(product)
                .then(
                    function (response) {
                        fetchSaveProductResponse(response);
                        debtService.loadDebts($rootScope.loggedUser);
                    },
                    function (error) {
                        console.error(error);
                    });
        };

        this.editProduct = function (product) {
            productFactory.editProduct(product)
                .then(
                    function (response) {
                        fetchEditProductResponse(response);
                        debtService.loadDebts($rootScope.loggedUser);
                    },
                    function (error) {
                        console.error(error);
                    });
        };

        this.deleteProduct = function (product) {
            productFactory.deleteProduct(product)
                .then(
                    function () {
                        fetchDeleteProductResponse(product);
                        debtService.loadDebts($rootScope.loggedUser);

                    },
                    function (error) {
                        console.error(error);
                    });
        };

        let fetchLoadProductsResponse = function (response) {
            products = response.data;
            userProducts = products.filter(prod => {
                return prod.creditor === $rootScope.loggedUser;
            });

            todaysUserProducts = userProducts.filter(prod => {
                return prod.creationDate === today;
            });
        };

        let fetchSaveProductResponse = function (response) {
            todaysUserProducts.push(response.data);
            userProducts.push(response.data);
            products.push(response.data);
        };

        let fetchEditProductResponse = function (response) {
            todaysUserProducts = todaysUserProducts.map(prod => editMap(prod));
            userProducts = userProducts.map(prod => editMap(prod));
            products = products.map(prod => editMap(prod));

            function editMap(prod) {
                if (prod.id === response.data.id) {
                    return response.data;
                } else {
                    return prod;
                }
            }
        };

        let fetchDeleteProductResponse = function (product) {
            todaysUserProducts = todaysUserProducts.filter(prod => {
                return prod.id !== product.id;
            });
            userProducts = userProducts.filter(prod => {
                return prod.id !== product.id;
            });
            products = products.filter(prod => {
                return prod.id !== product.id;
            });
        };
    });