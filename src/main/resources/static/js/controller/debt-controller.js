angular.module('ngBudgetCalc').controller('debtController', function ($scope, memberService, debtService, event, $rootScope) {
    $scope.debts = [];
    $scope.getDebtText = debt => debtService.getDebtText(debt);

    $rootScope.$on(event.GET_DEBTS, () => $scope.debts = debtService.getDebts());
    (() => debtService.loadDebts(memberService.getMember().name))();
});