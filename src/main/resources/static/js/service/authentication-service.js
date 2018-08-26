angular.module('ngBudgetCalc')
    .service('authenticationService', function ($http, $rootScope) {
        this.authenticate = (credentials, callback) => {
            let headers = credentials ? {
                authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)
            } : {};

            $http.get('/user', {headers: headers}).then(response => {
                $rootScope.authenticated = response.data.authenticated;
                callback && callback();
            }, response => {
                $rootScope.authenticated = false;
                callback && callback();
            })
        };
        this.isAuthenticated = () => $rootScope.authenticated;
    });