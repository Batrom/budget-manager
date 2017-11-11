angular.module('ngBudgetCalc')
    .factory('debtFactory', function ($http) {
        function getDebts(data) {
            return $http.get('/resource/getDebts?user=' + data);
        }

        return {
            getDebts: getDebts,
        }
    });
