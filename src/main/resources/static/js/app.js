angular.module('ngBudgetCalc', ['ngRoute', 'ngAnimate', 'ui.bootstrap', 'angular.filter'])
    .config(function ($routeProvider, $qProvider) {
        $routeProvider
            .when('/login', {
                templateUrl: 'view/login.html',
            })
            .when('/addProduct', {
                templateUrl: 'view/addProduct.html',
            })
            .when('/myProducts', {
                templateUrl: 'view/myProducts.html',
            })
            .when('/allProducts', {
                templateUrl: 'view/allProducts.html',
            })
            .when('/debts', {
                templateUrl: 'view/debts.html',
            })
            .otherwise({
                redirectTo: '/login'
            });
        $qProvider.errorOnUnhandledRejections(false);
    })
    .run(function ($rootScope, $location) {
        $rootScope.$on("$routeChangeStart", function (event, next) {
            if ($rootScope.loggedUser === null || $rootScope.loggedUser === undefined || $rootScope.loggedUser === "") {
                if (next.templateUrl !== "view/login.html") {
                    $location.path("/login");
                }
            }
        });
    })
    .directive('autofocus', [function () {
        return {
            restrict: 'A',
            link: function (scope, element) {
                element[0].focus();
            }
        };
    }])
    .filter('polishCurrency', function () {
        return function (input) {
            if (isNaN(input)) {
                return input;
            } else {
                return input + ' zł';
            }
        }
    });