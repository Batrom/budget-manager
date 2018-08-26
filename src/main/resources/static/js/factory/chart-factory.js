angular.module('ngBudgetCalc')
    .factory('chartFactory', function (restFactory, url) {
        return {
            getChartData: restFactory.get(url.getChartData)
        }
    });
