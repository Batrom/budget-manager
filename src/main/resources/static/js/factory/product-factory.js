angular.module('ngBudgetCalc')
    .factory('ProductFactory', function (RestFactory, url) {
        return {
            getProducts: RestFactory.get(url.getProducts),
            saveProduct: RestFactory.post(url.saveProduct),
            deleteProduct: RestFactory.post(url.deleteProduct),
            updateProduct: RestFactory.post(url.updateProduct),
            updateProductsCategory: RestFactory.post(url.updateProductsCategory),
        }
    });
