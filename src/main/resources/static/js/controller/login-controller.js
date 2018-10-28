angular.module('ngBudgetCalc').controller('LoginController', function ($scope, $location, $http, AuthenticationService, $rootScope, $httpParamSerializerJQLike) {
    redirectToHomeIfAlreadyAuthenticated();

    $scope.isLoginCorrect = true;
    $scope.isPasswordCorrect = true;

    $scope.credentials = {};
    $scope.logIn = () => {
        const credentialsAreValid = validateCredentials();
        if (credentialsAreValid) {
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
                        $scope.isLoginCorrect = false;
                        $scope.isPasswordCorrect = false;
                    }
                });
            }, response => {
                $location.path("/login");
                $rootScope.authenticated = false;
                $scope.isLoginCorrect = false;
                $scope.isPasswordCorrect = false;
            })
        }
    };

    function validateCredentials() {
        $scope.isLoginCorrect = !isEmpty($scope.credentials.username);
        $scope.isPasswordCorrect = !isEmpty($scope.credentials.password);
        return $scope.isLoginCorrect && $scope.isPasswordCorrect;
    }

    function redirectToHomeIfAlreadyAuthenticated() {
        AuthenticationService.authenticate().then(isAuthenticated => {
            if (isAuthenticated) $location.path("/home");
        });
    }
});