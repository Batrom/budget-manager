angular.module('ngBudgetCalc').controller('loginController', function ($scope, $location, $http, authenticationService) {
    redirectToHomeIfAlreadyAuthenticated();
    $scope.credentials = {};
    $scope.login = function () {
        authenticationService.authenticate($scope.credentials, function () {
            if (authenticationService.isAuthenticated()) {
                $location.path("/home");
                $scope.error = false;
            } else {
                $location.path("/login");
                $scope.error = true;
            }
        });
    };

    function redirectToHomeIfAlreadyAuthenticated() {
        if (authenticationService.isAuthenticated()) $location.path("/home");
    }
});