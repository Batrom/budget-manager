angular.module('ngBudgetCalc').controller('LoginController', function ($scope, $location, $http, AuthenticationService, $rootScope, $httpParamSerializerJQLike) {
    redirectToHomeIfAlreadyAuthenticated();

    $scope.credentials = {};
    $scope.logIn = () => {
        $http.post('login', $httpParamSerializerJQLike($scope.credentials), {
            headers: {
                "content-type": "application/x-www-form-urlencoded"
            }
        }).then(response => {
            AuthenticationService.authenticate().then(isAuthenticated => {
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
        AuthenticationService.authenticate().then(isAuthenticated => {
            if (isAuthenticated) $location.path("/home");
        });
    }
});