angular.module('ngBudgetCalc').controller('navController', function ($scope, $location, userService) {
    $scope.loggedUser = userService.getUser();
    $scope.isLoginView = function () {
        return $location.path() === '/login';
    };

    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path();
    };

    $scope.signOut = function () {
        userService.setUser('');
    };
});