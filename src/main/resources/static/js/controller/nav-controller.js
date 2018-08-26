angular.module('ngBudgetCalc').controller('navController', function ($scope, $location, memberService, $rootScope, event) {
    $scope.loggedMember = memberService.getMember();
    $rootScope.$on(event.GET_MEMBER, () => $scope.loggedMember = memberService.getMember());
    $scope.isHomeOrLoginView = function () {
        return $location.path() === '/home' || $location.path() === '/login';
    };

    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path();
    };

    $scope.logOut = function () {
        memberService.remove();
    };
});