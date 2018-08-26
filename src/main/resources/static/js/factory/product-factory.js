angular.module('ngBudgetCalc')
    .factory('productFactory', function (restFactory, url) {
        return {
            getProducts: restFactory.get(url.getProducts),
            saveProduct: restFactory.post(url.saveProduct),
            deleteProduct: restFactory.post(url.deleteProduct),
            updateProduct: restFactory.post(url.updateProduct),
            updateProductsCategory: restFactory.post(url.updateProductsCategory),
            getProductsByCategory: restFactory.get(url.getProductsByCategory)
        }
    });
