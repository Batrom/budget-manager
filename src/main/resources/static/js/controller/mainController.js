angular.module('ngBudgetCalc')
    .controller('mainController', function functionName($scope, productService, debtService, $rootScope, $uibModal) {
        $scope.productService = productService;
        $rootScope.loggedUser = '';
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
                "creditor": $rootScope.loggedUser
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
            $rootScope.loggedUser = u;
            $scope.init();
        };

        $scope.signOut = function () {
            $rootScope.loggedUser = '';
        };

        $scope.init = function () {
            $scope.product = {
                "debtorsGroup": "Wszyscy",
                "creditor": $rootScope.loggedUser
            };
            productService.loadProducts();
        };
    });



