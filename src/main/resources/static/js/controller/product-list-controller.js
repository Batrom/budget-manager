angular.module('ngBudgetCalc').controller('ProductListController', function ($scope, ProductService, $uibModal, MemberService, Event, EventService, CommonDataService) {
    $scope.products = ProductService.getAllProducts();
    $scope.memberGroups = CommonDataService.getMemberGroups();

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

    $scope.canEdit = function (product) {
        return MemberService.getMember().name === product.creditor && product.canEdit;
    };

    executeIfEmpty($scope.products, () => ProductService.loadProducts({member: MemberService.getMember().name}));

    EventService.addListener(Event.PRODUCTS_CHANGED, $scope, () => {
        $scope.products = ProductService.getAllProducts();
    });

    EventService.addListener(Event.MEMBER_CHANGED, $scope, () => {
        $scope.memberGroups = CommonDataService.getMemberGroups();
    })
});
