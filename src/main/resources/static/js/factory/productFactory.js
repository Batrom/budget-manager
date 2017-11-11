angular.module('ngBudgetCalc')
    .factory('productFactory', function ($http) {
        function getProducts() {
            return $http.get('/resource/getProducts');
        }

        function saveProduct(data) {
            return $http.post('/resource/saveProduct', data);
        }

        function editProduct(data) {
            return $http.post('/resource/editProduct', data);
        }

        function deleteProduct(data) {
            return $http.post('/resource/deleteProduct', data);
        }


        return {
            getProducts: getProducts,
            saveProduct: saveProduct,
            deleteProduct: deleteProduct,
            editProduct: editProduct
        }
    });
