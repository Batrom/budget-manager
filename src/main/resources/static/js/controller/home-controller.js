angular.module('ngBudgetCalc').controller('HomeController', function ($scope, CommonDataService, MemberService) {
    $scope.innerLogIn = memberName => MemberService.setMember(memberName);
    CommonDataService.loadCommonData();
});