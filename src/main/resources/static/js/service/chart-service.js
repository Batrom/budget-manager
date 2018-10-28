angular.module('ngBudgetCalc')
    .service('ChartService', function (ChartFactory, RestService, Event) {
        let data = [];
        const months = ["styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"];

        let formatChartData = function (data) {
            return data.sort((a, b) => {
                a = a.date.split('.').reverse().join('');
                b = b.date.split('.').reverse().join('');
                return a > b ? -1 : a < b ? 1 : 0;
            }).map(data => {
                let dataArray = data.date.split('.');
                return {
                    date: months[dataArray[0] - 1] + ' ' + dataArray[1],
                    labels: data.labelValueList.map(labelValue => labelValue.key),
                    values: data.labelValueList.map(labelValue => labelValue.value)
                };
            });
        };
        let loadChartDataCallback = function (response) {
            return RestService.fetchData(() => data = formatChartData(response.data), Event.CHART_DATA_CHANGED)
        };
        this.getData = () => data;
        this.loadChartData = ChartFactory.getChartData(loadChartDataCallback);

        this.clearData = () => {
            data = [];
        }
    });