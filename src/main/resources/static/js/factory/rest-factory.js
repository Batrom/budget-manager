angular.module('ngBudgetCalc')
    .factory('RestFactory', function ($http) {
        function post(url) {
            return (successCallback, errorCallback = response => console.error(response)) => (data = {}) => {
                $http.post(url, data).then(response => successCallback(response), response => errorCallback(response));
            }
        }

        function get(url) {
            return (successCallback, errorCallback = response => console.error(response)) => (data = '') => {
                $http.get(createGetUrl(url, data)).then(response => successCallback(response), response => errorCallback(response));
            }
        }

        function createGetUrl(url, data) {
            if (isEmpty(data)) return url;
            else return url + '?' + Object.entries(data).map(entry => entry[0] + '=' + entry[1]).reduce((prev, next) => prev + '&' + next);
        }

        return {
            post: post,
            get: get
        }
    });
