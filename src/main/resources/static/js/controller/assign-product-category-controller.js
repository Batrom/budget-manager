angular.module('ngBudgetCalc').controller('AssignProductCategoryController', function ($scope, MemberService, ProductService, CommonDataService, Event, $route, EventService, $rootScope) {
    $scope.productsByCategory = ProductService.getProductsByCategory();
    $scope.productCategories = CommonDataService.getProductsCategories();

    $scope.leftColumnCategoryType = $scope.productCategories.filter(cat => cat.name === 'NONE')[0];
    $scope.middleColumnCategoryType = $scope.productCategories.filter(cat => cat.name === 'FOOD')[0];
    $scope.rightColumnCategoryType = $scope.productCategories.filter(cat => cat.name === 'HOME')[0];

    $scope.getCategoryByName = function (name) {
        return $scope.productCategories.find(cat => cat.name === name);
    };

    $scope.getLeftColumnProducts = function () {
        return $scope.productCategories[leftColumnCategoryType.name]
    };

    $scope.getMiddleColumnProducts = function () {
        return $scope.productCategories[middleColumnCategoryType.name]
    };

    $scope.getRightColumnProducts = function () {
        return $scope.productCategories[rightColumnCategoryType.name]
    };

    executeIfEmpty($scope.productsByCategory, () => {
        ProductService.loadProducts({member: MemberService.getMember().name})
    });

    EventService.addListener(Event.PRODUCTS_CHANGED, $scope, () => {
        $scope.productsByCategory = ProductService.getProductsByCategory();
        if (isNotEmpty($scope.productsByCategory)) $route.reload();
    });

    EventService.addListener(Event.COMMON_DATA_CHANGED, $scope, () => {
        $scope.productCategories = CommonDataService.getProductsCategories();
        $scope.productsByCategory = ProductService.getProductsByCategory();
        $route.reload();
    });

    EventService.addListener('$routeChangeStart', $scope, (event, next, before) => {
        if (before.$$route.originalPath === '/product/category' && next.$$route.originalPath !== '/product/category') {
            ProductService.updateProductsCategory($scope.productsByCategory);
        }
    });
});