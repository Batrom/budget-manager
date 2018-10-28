angular.module('ngBudgetCalc')
    .factory('CommonDataFactory', function (RestFactory, url) {
        return {
            getCommonData: RestFactory.get(url.getCommonData)
        }
    });
