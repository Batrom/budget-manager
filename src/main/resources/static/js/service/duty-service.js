angular.module('ngBudgetCalc')
    .service('DutyService', function (DutyFactory, RestService, Event) {
        let duties = [];

        this.getDuties = function () {
            return duties;
        };

        this.loadDuties = DutyFactory.getDuties(response => loadDutiesCallback(response));
        this.saveDuty = DutyFactory.saveDuty(response => saveDutyCallback(response));
        this.deleteDuty = DutyFactory.deleteDuty(response => deleteDutyCallback(response.data));
        this.updateDuty = DutyFactory.updateDuty(response => updateDutyCallback(response.data));

        let loadDutiesCallback = function (response) {
            return RestService.fetchData(() => duties = response.data, Event.DUTIES_CHANGED);
        };

        let saveDutyCallback = function (response) {
            return RestService.fetchData(() => duties.push(response.data), Event.DUTIES_CHANGED);
        };

        let deleteDutyCallback = function (response) {
            return RestService.fetchData(() => duties = duties.filter(d => d.id !== response.data), Event.DUTIES_CHANGED);
        };

        let updateDutyCallback = function (response) {
            return RestService.fetchData(() => duties = duties.map(duty => getRightIfEquals(duty, response.data)), Event.DUTIES_CHANGED);
        };

        this.clearData = () => {
            duties = [];
        }
    });