angular.module('ngBudgetCalc')
    .factory('debtFactory', function (restFactory, url) {
        return {
            getDebts: restFactory.get(url.getDebts)
        }
    });
