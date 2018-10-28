angular.module('ngBudgetCalc').service('RestService', function ($rootScope) {
    this.fetchData = (fetchFunction, event) => {
        fetchFunction();
        if (Array.isArray(event)) event.forEach(e => $rootScope.$emit(e));
        else $rootScope.$emit(event);
    };
});