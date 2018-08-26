angular.module('ngBudgetCalc').controller('mainController', function (authenticationService) {
    authenticationService.authenticate();
});