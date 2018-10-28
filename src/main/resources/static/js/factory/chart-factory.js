angular.module('ngBudgetCalc')
    .factory('ChartFactory', function (RestFactory, url) {
        return {
            getChartData: RestFactory.get(url.getChartData)
        }
    });
