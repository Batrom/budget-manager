angular.module('ngBudgetCalc').controller('homeController', function ($scope, initDataService, memberService) {
    $scope.logIn = memberName => memberService.setMember(memberName);
    initDataService.loadInitData();
});