angular.module('ngBudgetCalc')
    .controller('mainController', function functionName($scope, productService, debtService, $uibModal, userService) {
        $scope.productService = productService;
        $scope.product = {};

        $scope.$watch(function () {
            return productService.getProducts()
        }, function (newVal) {
            $scope.products = newVal;
        });
        $scope.$watch(function () {
            return productService.getUserProducts()
        }, function (newVal) {
            $scope.userProducts = newVal;
        });
        $scope.$watch(function () {
            return productService.getTodaysUserProducts()
        }, function (newVal) {
            $scope.todaysUserProducts = newVal;
        });
        $scope.$watch(function () {
            return debtService.getDebts()
        }, function (newVal) {
            $scope.debts = newVal;
        });

        $scope.getDebtText = function (debt) {
            return debtService.getDebtText(debt);
        };

        $scope.save = function () {
            productService.saveProduct($scope.product);

            $scope.product = {
                "debtorsGroup": "Wszyscy",
                "creditor": userService.getUser().name
            };
            document.getElementById('productDescription').focus();
        };


        $scope.delete = function (product) {
            productService.deleteProduct(product);
        };

        $scope.openEditModal = function (product) {
            $uibModal.open({
                templateUrl: 'editProductModal.html',
                controller: 'editProductModalController',
                resolve: {
                    product: function () {
                        return product;
                    }
                }
            });
        };

        $scope.signIn = function (u) {
            userService.setUser(u);
            $scope.init();
        };

        $scope.signOut = function () {
            userService.setUser('');
        };

        $scope.init = function () {
            $scope.product = {
                "debtorsGroup": "Wszyscy",
                "creditor": userService.getUser().name
            };
            productService.loadProducts();
        };
    });



