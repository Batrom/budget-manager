angular.module('ngBudgetCalc', ['ngRoute', 'ngAnimate', 'ui.bootstrap', 'angular.filter', 'ngCookies', 'ui.calendar', 'ui.tree'])
    .config(function ($routeProvider, $qProvider, products, $httpProvider) {
        $routeProvider
            .when('/login', {
                templateUrl: 'view/login.html',
                controller: 'loginController'
            })
            .when('/home', {
                templateUrl: 'view/home.html',
                controller: 'homeController'
            })
            .when('/addProduct', {
                templateUrl: 'view/addProduct.html',
                controller: 'productController',
                resolve: {
                    productsType: () => products.TODAYS_MEMBER
                }
            })
            .when('/myProducts', {
                templateUrl: 'view/myProducts.html',
                controller: 'productController',
                resolve: {
                    productsType: () => products.MEMBER
                }
            })
            .when('/allProducts', {
                templateUrl: 'view/allProducts.html',
                controller: 'productController',
                resolve: {
                    productsType: () => products.ALL
                }
            })
            .when('/debts', {
                templateUrl: 'view/debts.html',
                controller: 'debtController'
            })
            .when('/duties', {
                templateUrl: 'view/duties.html'
            })
            .when('/assignCategory', {
                templateUrl: 'view/assignProductCategory.html',
                controller: 'AssignProductCategoryController'
            })
            .when('/charts', {
                templateUrl: 'view/charts.html',
                controller: 'chartController'
            })
            .otherwise({
                redirectTo: '/login'
            });
        $qProvider.errorOnUnhandledRejections(false);
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .run(function ($location, $rootScope, authenticationService) {
        $rootScope.$on("$routeChangeStart", function (event, next) {
            if (!$rootScope.authenticated) $location.path("/login");
            authenticationService.authenticate().then(isAuthenticated => {
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