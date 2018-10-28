angular.module('ngBudgetCalc')
    .service('AuthenticationService', function ($http, $rootScope, $q) {
        this.authenticate = () => {
            let deferred = $q.defer();
            if ($rootScope.authenticated) deferred.resolve(true);
            else {
                $http.get('user').then(response => {
                    $rootScope.authenticated = !!response.data.authenticated;
                    deferred.resolve($rootScope.authenticated)
                }, response => {
                    $rootScope.authenticated = false;
                    deferred.resolve(false);
                })
            }
            return deferred.promise;
        };

        this.clearData = () => {
            $rootScope.authenticated = false;
        }
    });