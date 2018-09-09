angular.module('ngBudgetCalc')
    .controller('productController', function functionName($scope, productService, $uibModal, initDataService, memberService, $rootScope, event, products, productsType) {
        $scope.memberGroups = initDataService.getMemberGroups();
        $scope.product = {
            debtorGroup: $scope.memberGroups[0],
            creditor: memberService.getMember().name
        };
        $scope.products = [];

        $rootScope.$on(event.PRODUCTS_CHANGED, () => {
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

        $scope.openUpdateProductModal = function (product) {
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

        productService.loadProducts();
    });



