angular.module('ngBudgetCalc').controller('NavController', function ($scope, $location, MemberService, EventService, Event, $http, $rootScope) {
    $scope.loggedMember = MemberService.getMember();

    $scope.isHomeView = () => $location.path() === '/home';
    $scope.isLoginView = () => $location.path() === '/login';
    $scope.isActive = viewLocation => viewLocation === $location.path();
    $scope.innerLogOut = () => MemberService.remove();
    $scope.logOut = () => {
        $http.post('logout', {}).then(response => {
            $rootScope.authenticated = false;
            $location.path("/login");
        }, response => {
            $rootScope.authenticated = false;
        });
    };

    EventService.addListener(Event.MEMBER_CHANGED, $scope, () => {
        $scope.loggedMember = MemberService.getMember()
    });
});