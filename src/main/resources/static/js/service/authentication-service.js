angular.module('ngBudgetCalc')
    .service('authenticationService', function ($http, $rootScope, $q) {
        this.authenticate = credentials => {
            let deferred = $q.defer();
            if ($rootScope.authenticated) deferred.resolve(true);
            else {
                let headers = credentials ? {
                    authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)
                } : {};
                $http.get('/user', {headers: headers}).then(response => {
                    $rootScope.authenticated = !!response.data.authenticated;
                    deferred.resolve($rootScope.authenticated)
                }, response => {
                    $rootScope.authenticated = false;
                    deferred.resolve(false);
                })
            }
            return deferred.promise;
        };
    });