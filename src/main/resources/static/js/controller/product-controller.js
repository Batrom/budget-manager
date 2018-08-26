angular.module('ngBudgetCalc')
    .controller('productController', function functionName($scope, productService, $uibModal, initDataService, memberService, $rootScope, event, products, productsType) {
        $scope.memberGroups = initDataService.getMemberGroups();
        $scope.product = {
            debtorGroup: $scope.memberGroups[0],
            creditor: memberService.getMember().name
        };
        $scope.products = [];

        /*$scope.$watch(function () {
            return productService.getProducts()
        }, function (newVal) {
            $scope.products = newVal;
        });
        $scope.$watch(function () {
            return productService.getMemberProducts()
        }, function (newVal) {
            $scope.memberProducts = newVal;
        });
        $scope.$watch(function () {
            return productService.getTodaysMemberProducts()
        }, function (newVal) {
            $scope.todaysMemberProducts = newVal;
        });
        $scope.$watch(dutyService.getDuties,
            function (newVal) {
                $scope.duties = newVal;
            });
        $scope.$watch(function () {
            return initDataService.getMemberGroups();
        }, function (newVal) {
            $scope.memberGroups = newVal;
            if ($scope.product.debtorGroup === undefined && $scope.memberGroups.length > 0) $scope.product.debtorGroup = $scope.memberGroups[0];
        });*/

        $rootScope.$on(event.GET_PRODUCTS, () => {
            switch (productsType) {
                case products.ALL:
                    $scope.products = productService.getAllProducts();
                    break;
                case products.MEMBER:
                    $scope.products = productService.getMemberProducts();
                    break;
                case products.TODAYS_MEMBER:
                    $scope.products = productService.getTodaysMemberProducts();
                    break;
            }
        });

        $scope.saveProduct = function () {
            productService.saveProduct($scope.product);
            $scope.product = {
                debtorGroup: $scope.memberGroups[0],
                creditor: memberService.getMember().name
            };
            angular.element(document.querySelector('#productDescription'))[0].focus();
        };

        $scope.deleteProduct = function (product) {
            productService.deleteProduct(product);
        };

        $scope.openEditModal = function (product) {
            $uibModal.open({
                templateUrl: 'view/updateProductModal.html',
                controller: 'updateProductModalController',
                resolve: {
                    product: () => product,
                    memberGroups: () => $scope.memberGroups
                }
            }).result.then(result => {
                product.description = result.description;
                product.price = result.price;
                product.debtorGroup = result.debtorGroup;
            });
        };

        (() => productService.loadProducts())();
    });



