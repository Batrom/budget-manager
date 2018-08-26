angular.module('ngBudgetCalc')
    .factory('restFactory', function ($http) {
        function post(url) {
            return (success, error = response => console.error(response)) => (data = {}) => {
                $http.post(url, data).then(response => success(response), response => error(response));
            }
        }

        function get(url) {
            return (success, error = response => console.error(response)) => (data = '') => {
                $http.get(url + data).then(response => success(response), response => error(response));
            }
        }

        return {
            post: post,
            get: get
        }
    });
