angular.module('ngBudgetCalc').controller('loginController', function ($scope, userService) {
    $scope.setUser = function (u) {
        userService.setUser(u);
    };
});