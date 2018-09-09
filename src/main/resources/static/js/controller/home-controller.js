angular.module('ngBudgetCalc').controller('homeController', function ($scope, initDataService, memberService, $http, $rootScope, $location) {
    $scope.innerLogIn = memberName => memberService.setMember(memberName);
    initDataService.loadInitData();
});