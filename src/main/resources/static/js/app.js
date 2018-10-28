angular.module('ngBudgetCalc', ['ngRoute', 'ngAnimate', 'ui.bootstrap', 'angular.filter', 'ngCookies', 'ui.calendar', 'ui.tree', 'ngDragDrop'])
    .config(function ($routeProvider, $qProvider, $httpProvider) {
        $routeProvider
            .when('/login', {
                templateUrl: 'view/login.html',
                controller: 'LoginController'
            })
            .when('/home', {
                templateUrl: 'view/home.html',
                controller: 'HomeController'
            })
            .when('/product/new', {
                templateUrl: 'view/new-product.html',
                controller: 'NewProductController'
            })
            .when('/product/list', {
                templateUrl: 'view/product-list.html',
                controller: 'ProductListController'
            })
            .when('/debts', {
                templateUrl: 'view/debts.html',
                controller: 'DebtController'
            })
            .when('/duties', {
                templateUrl: 'view/duties.html'
            })
            .when('/product/category', {
                templateUrl: 'view/assign-product-category.html',
                controller: 'AssignProductCategoryController'
            })
            .when('/charts', {
                templateUrl: 'view/chart.html',
                controller: 'ChartController'
            })
            .otherwise({
                redirectTo: '/login'
            });
        $qProvider.errorOnUnhandledRejections(false);
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .run(function ($location, $rootScope, AuthenticationService, CleanupService) {
        $rootScope.$on("$routeChangeStart", function (event, next) {
            AuthenticationService.authenticate().then(isAuthenticated => {
                if (isAuthenticated) {
                    if (next.$$route === undefined || next.$$route.originalPath === '/login') {
                        $location.path("/home");
                    } else {
                        $location.path(next.$$route.originalPath);
                    }
                }
                else {
                    $location.path("/login");
                }
            });

            if (next.$$route !== undefined && next.$$route.originalPath === '/home') {
                CleanupService.softClear();
            }

            if (next.$$route !== undefined && next.$$route.originalPath === '/login') {
                CleanupService.hardClear();
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