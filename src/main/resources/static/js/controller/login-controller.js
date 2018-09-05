angular.module('ngBudgetCalc').controller('loginController', function ($scope, $location, $http, authenticationService) {
    redirectToHomeIfAlreadyAuthenticated();
    $scope.credentials = {};
    $scope.login = function () {
        authenticationService.authenticate($scope.credentials).then(isAuthenticated => {
            if (isAuthenticated) {
                $location.path("/home");
            } else {
                $location.path("/login");
            }
        });
    };

    function redirectToHomeIfAlreadyAuthenticated() {
        authenticationService.authenticate().then(isAuthenticated => {
            if (isAuthenticated) $location.path("/home");
        });
    }
});