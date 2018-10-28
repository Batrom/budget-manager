angular.module('ngBudgetCalc').controller('NewProductController', function ($scope, ProductService, $uibModal, CommonDataService, MemberService, Event, EventService) {
    $scope.memberGroups = CommonDataService.getMemberGroups();
    $scope.newProduct = createEmptyProduct();
    $scope.todaysMemberProducts = ProductService.getTodaysMemberProducts();

    $scope.saveProduct = function () {
        ProductService.saveProduct($scope.newProduct);
        $scope.newProduct = createEmptyProduct();
        angular.element(document.querySelector('#productDescription'))[0].focus();
    };

    $scope.deleteProduct = function (product) {
        ProductService.deleteProduct(product);
    };

    $scope.openUpdateProductModal = function (product) {
        $uibModal.open({
            templateUrl: 'view/update-product-modal.html',
            controller: 'UpdateProductModalController',
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

    function createEmptyProduct() {
        return {
            debtorGroup: CommonDataService.getMemberGroups()[0],
            creditor: MemberService.getMember().name
        };
    }

    executeIfEmpty($scope.todaysMemberProducts, () => ProductService.loadProducts());

    EventService.addListener(Event.PRODUCTS_CHANGED, $scope, () => {
        $scope.todaysMemberProducts = ProductService.getTodaysMemberProducts();
    });

    EventService.addListener(Event.MEMBER_CHANGED, $scope, () => {
        $scope.memberGroups = CommonDataService.getMemberGroups();
    })
});