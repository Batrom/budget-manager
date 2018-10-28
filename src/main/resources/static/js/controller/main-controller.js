angular.module('ngBudgetCalc').controller('MainController', function (MemberService, CommonDataService) {
    if (MemberService.getMember().name !== '') CommonDataService.loadCommonData();
});