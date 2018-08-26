angular.module('ngBudgetCalc')
    .service('restService', function ($rootScope) {
        this.fetchData = (fetchFunction, event) => {
            fetchFunction();
            if (Array.isArray(event)) event.forEach(e => $rootScope.$emit(e));
            else $rootScope.$emit(event);
        };

        this.on = (event, callback) => {
            $rootScope.$on(event, callback);
        }
    });