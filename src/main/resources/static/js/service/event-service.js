angular.module('ngBudgetCalc').service('EventService', function ($rootScope) {
        this.addListener = (event, scope, callback) => {
            let deregister = $rootScope.$on(event, (event_, next, before) => {
                callback(event_, next, before);
            });
            scope.$on('$destroy', deregister);
        }
    }
);