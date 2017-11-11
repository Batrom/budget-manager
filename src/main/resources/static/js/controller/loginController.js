angular.module('ngBudgetCalc').controller('loginController', function ($scope, $rootScope) {
    $scope.setUser = function (u) {
        $rootScope.loggedUser = u;
    };
});