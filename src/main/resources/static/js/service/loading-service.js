angular.module('ngBudgetCalc').service('LoadingService', function ($rootScope, Event, $timeout) {
    let loading = false;
    this.setLoading = value => {
        if (!value) {
            $timeout(() => {
                loading = value;
                $rootScope.$emit(Event.LOADING_CHANGED);
            }, 300)
        } else {
            loading = value;
            $rootScope.$emit(Event.LOADING_CHANGED);
        }
    };
    this.getLoading = () => loading;
});