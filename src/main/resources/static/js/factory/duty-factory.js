angular.module('ngBudgetCalc')
    .factory('dutyFactory', function (restFactory, url) {
        return {
            getDuties: restFactory.get(url.getDuties),
            saveDuty: restFactory.post(url.saveDuty),
            deleteDuty: restFactory.post(url.deleteDuty),
            updateDuty: restFactory.post(url.updateDuty)
        }
    });
