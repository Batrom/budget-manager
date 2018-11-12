angular.module('ngBudgetCalc').controller('MainController', function ($scope, MemberService, CommonDataService, EventService, Event, LoadingService) {
    $scope.loading = false;
    if (MemberService.getMember().name !== '') CommonDataService.loadCommonData();

    EventService.addListener(Event.LOADING_CHANGED, $scope, () => {
        $scope.loading = LoadingService.getLoading();
    });
});