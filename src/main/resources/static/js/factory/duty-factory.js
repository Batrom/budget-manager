angular.module('ngBudgetCalc')
    .factory('DutyFactory', function (RestFactory, url) {
        return {
            getDuties: RestFactory.get(url.getDuties),
            saveDuty: RestFactory.post(url.saveDuty),
            deleteDuty: RestFactory.post(url.deleteDuty),
            updateDuty: RestFactory.post(url.updateDuty)
        }
    });
