angular.module('ngBudgetCalc')
    .service('CleanupService', function (AuthenticationService, ProductService, DutyService, CommonDataService, DebtService, ChartService) {
        this.softClear = () => {
            ProductService.clearData();
            DutyService.clearData();
            DebtService.clearData();
            ChartService.clearData();
        };

        this.hardClear = () => {
            AuthenticationService.clearData();
            CommonDataService.clearData();
            this.softClear();
        };
    });