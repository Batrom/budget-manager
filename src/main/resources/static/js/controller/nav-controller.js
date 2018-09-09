angular.module('ngBudgetCalc').controller('navController', function ($scope, $location, memberService, $rootScope, event, $http) {
    $scope.loggedMember = memberService.getMember();
    $rootScope.$on(event.MEMBER_CHANGED, () => $scope.loggedMember = memberService.getMember());

    $scope.isHomeView = () => $location.path() === '/home';
    $scope.isLoginView = () => $location.path() === '/login';
    $scope.isActive = viewLocation => viewLocation === $location.path();
    $scope.innerLogOut = () => memberService.remove();
    $scope.logOut = () => {
        $http.post('logout', {}).then(response => {
            $rootScope.authenticated = false;
            $location.path("/login");
        }, response => {
            $rootScope.authenticated = false;
        });
    }
});