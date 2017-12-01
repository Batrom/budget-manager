angular.module('ngBudgetCalc')
    .service('userService', function ($cookies) {
       let loggedUser = {
           'name': $cookies.get('loggedUser') || ''
       };

       this.getUser = function () {
           return loggedUser
       };

       this.setUser = function (user) {
           $cookies.put('loggedUser', user);
           loggedUser.name = user;
       }
    });