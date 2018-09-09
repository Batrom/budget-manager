angular.module('ngBudgetCalc').controller('AssignProductCategoryController', function ($scope, memberService, productService, initDataService, event, $rootScope) {
    $scope.productsByCategory = [];
    $scope.productCategories = initDataService.getProductsCategories();

    $scope.$on("$destroy", function () {
        $scope.updateProductsCategory();
    });
    $rootScope.$on(event.GET_PRODUCTS_BY_CATEGORY, () => $scope.productsByCategory = productService.getProductsByCategory());

    $scope.getCategoryByName = function (name) {
        return $scope.productCategories.find(cat => cat.name === name);
    };

    $scope.getProductsByCategory = function (cat) {
        return $scope.productsByCategory.find(prod => prod.category === cat);
    };

    $scope.updateProductsCategory = function () {
        productService.updateProductsCategory($scope.productsByCategory);
    };

    productService.loadProductsByCategory(memberService.getMember().name);
});