angular.module('ngBudgetCalc').factory('userFactory', function () {
    let user = '';
    function getUser() {
        return user;
    }
    function setUser(u) {
        user = u;
    }
    return {
        getUser: getUser,
        setUser: setUser
    }
});