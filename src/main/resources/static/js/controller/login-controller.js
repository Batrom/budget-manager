angular.module('ngBudgetCalc').controller('loginController', function ($scope, $location, $http, authenticationService, $rootScope, $httpParamSerializerJQLike) {
    redirectToHomeIfAlreadyAuthenticated();

    $scope.credentials = {};
    $scope.logIn = () => {
        $http.post('login', $httpParamSerializerJQLike($scope.credentials), {
            headers: {
                "content-type": "application/x-www-form-urlencoded"
            }
        }).then(response => {
            authenticationService.authenticate().then(isAuthenticated => {
                if (isAuthenticated) {
                    $location.path("/home");
                } else {
                    $location.path("/login");
                }
            });
        }, response => {
            $location.path("/login");
            $rootScope.authenticated = false;
        })
    };

    function redirectToHomeIfAlreadyAuthenticated() {
        authenticationService.authenticate().then(isAuthenticated => {
            if (isAuthenticated) $location.path("/home");
        });
    }
});