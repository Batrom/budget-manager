angular.module('ngBudgetCalc', ['ngRoute', 'ngAnimate', 'ui.bootstrap', 'angular.filter', 'ngCookies', 'ui.calendar', 'ui.tree'])
    .config(function ($routeProvider, $qProvider, products, $httpProvider) {
        $routeProvider
            .when('/login', {
                templateUrl: 'view/login.html',
                controller: 'loginController'
            })
            .when('/home', {
                templateUrl: 'view/home.html',
                controller: 'homeController',
                authenticated: true
            })
            .when('/addProduct', {
                templateUrl: 'view/addProduct.html',
                controller: 'productController',
                resolve: {
                    productsType: () => products.TODAYS_MEMBER
                },
                authenticated: true
            })
            .when('/myProducts', {
                templateUrl: 'view/myProducts.html',
                controller: 'productController',
                resolve: {
                    productsType: () => products.MEMBER
                },
                authenticated: true
            })
            .when('/allProducts', {
                templateUrl: 'view/allProducts.html',
                controller: 'productController',
                resolve: {
                    productsType: () => products.ALL
                },
                authenticated: true
            })
            .when('/debts', {
                templateUrl: 'view/debts.html',
                controller: 'debtController',
                authenticated: true
            })
            .when('/duties', {
                templateUrl: 'view/duties.html',
                authenticated: true
            })
            .when('/assignCategory', {
                templateUrl: 'view/assignProductCategory.html',
                controller: 'AssignProductCategoryController',
                authenticated: true
            })
            .when('/charts', {
                templateUrl: 'view/charts.html',
                controller: 'chartController',
                authenticated: true
            })
            .otherwise({
                redirectTo: '/home'
            });
        $qProvider.errorOnUnhandledRejections(false);
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .run(function ($location, $rootScope, authenticationService) {
        $rootScope.$on("$routeChangeStart", function (event, next) {
            if (authenticationService.isAuthenticated()) {
                if (next.$$route !== undefined && next.$$route.originalPath === '/login') {
                    $location.path("/home");
                }
            } else {
                if (next.$$route === undefined || next.$$route.authenticated) {
                    $location.path('/login');
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