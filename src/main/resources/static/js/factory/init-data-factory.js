angular.module('ngBudgetCalc')
    .factory('initDataFactory', function (restFactory, url) {
        return {
            getInitData: restFactory.get(url.getInitData)
        }
    });
