angular.module('ngBudgetCalc')
    .factory('RestFactory', function ($http) {
        function post(url) {
            return (successCallback, errorCallback = response => console.error(response)) => (data = {}) => {
                $http.post(url, data).then(response => successCallback(response), response => errorCallback(response));
            }
        }

        function get(url) {
            return (successCallback, errorCallback = response => console.error(response)) => (data = '') => {
                $http.get(url + data).then(response => successCallback(response), response => errorCallback(response));
            }
        }

        return {
            post: post,
            get: get
        }
    });
