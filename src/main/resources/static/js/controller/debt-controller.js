angular.module('ngBudgetCalc').controller('DebtController', function ($scope, MemberService, DebtService, Event, EventService, $sce) {
    $scope.debts = DebtService.getDebts();
    $scope.getDebtText = debt => $sce.trustAsHtml(DebtService.getDebtText(debt));

    executeIfEmpty($scope.products, () => DebtService.loadDebts({member: MemberService.getMember().name}));

    EventService.addListener(Event.DEBTS_CHANGED, $scope, () => {
        $scope.debts = DebtService.getDebts();
    })
});