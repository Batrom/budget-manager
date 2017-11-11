angular.module('ngBudgetCalc').controller('navController', function ($scope, $location) {
    $scope.isLoginView = function () {
        return $location.path() === '/login';
    };

    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path();
    };
});