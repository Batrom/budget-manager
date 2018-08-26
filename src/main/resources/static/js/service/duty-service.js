angular.module('ngBudgetCalc')
    .service('dutyService', function (dutyFactory, restService, event) {
        let duties = [];

        this.getDuties = function () {
            return duties;
        };

        this.loadDuties = dutyFactory.getDuties(response => fetchLoadDutiesResponse(response));
        this.saveDuty = dutyFactory.saveDuty(response => fetchSaveDutyResponse(response));
        this.deleteDuty = dutyFactory.deleteDuty(response => fetchDeleteDutyResponse(response.data));
        this.updateDuty = dutyFactory.updateDuty(response => fetchUpdateDutyResponse(response.data));

        let fetchLoadDutiesResponse = function (response) {
            return restService.fetchData(() => duties = response.data, event.GET_DUTIES);
        };

        let fetchSaveDutyResponse = function (response) {
            return restService.fetchData(() => duties.push(response.data), event.GET_DUTIES);
        };

        let fetchDeleteDutyResponse = function (response) {
            return restService.fetchData(() => duties = duties.filter(d => d.id !== response.data.id), event.GET_DUTIES);
        };

        let fetchUpdateDutyResponse = function (response) {
            return restService.fetchData(() => duties = duties.map(duty => getRightIfEquals(duty, response.data)), event.GET_DUTIES);
        };
    });