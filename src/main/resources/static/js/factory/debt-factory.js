angular.module('ngBudgetCalc')
    .factory('DebtFactory', function (RestFactory, url) {
        return {
            getDebts: RestFactory.get(url.getDebts)
        }
    });
