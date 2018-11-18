angular.module('ngBudgetCalc').controller('ChartController', function ($scope, ChartService, Event, MemberService, EventService, CommonDataService) {
    $scope.chartData = [{labels: [''], values: []}];
    $scope.selectedData = $scope.chartData[0];
    $scope.memberGroups = CommonDataService.getMemberGroups();
    $scope.chosenMemberGroup = $scope.memberGroups.filter(group => group === MemberService.getMember().name)[0] || $scope.memberGroups[0];

    $scope.cachedChosenMonthIndex = 0;
    $scope.reloadData = () => {
        $scope.cachedChosenMonthIndex = $scope.chartData.indexOf($scope.selectedData) || 0;
        ChartService.loadChartData({memberGroup: $scope.chosenMemberGroup});
    };

    $scope.memberGroupsSelectSize = () => $scope.memberGroups.length !== 0 ? ($scope.memberGroups.length > 6 ? 6 : $scope.memberGroups.length) : 1;
    $scope.monthSelectSize = () => $scope.chartData.length !== 0 ? ($scope.chartData.length > 10 ? 10 : $scope.chartData.length) : 1;

    Chart.defaults.global.defaultFontFamily = "'Lato', sans-serif";
    let myChart = new Chart(angular.element(document.querySelector('#chart'))[0], {
        type: 'doughnut',
        data: {
            labels: $scope.selectedData.labels,
            datasets: [{
                data: $scope.selectedData.values,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.5)',
                    'rgba(54, 162, 235, 0.5)',
                    'rgba(255, 206, 86, 0.5)',
                    'rgba(75, 192, 192, 0.5)',
                    'rgba(153, 102, 255, 0.5)',
                    'rgba(255, 159, 64, 0.5)',
                    'rgba(15, 247, 255, 0.5)'
                ],
                borderColor: [
                    'rgba(255,99,132,1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)',
                    'rgba(255, 159, 64, 1)',
                    'rgba(15, 247, 255, 1)'
                ],
                borderWidth: 1,
                datalabels: {
                    anchor: 'center',
                    backgroundColor: null,
                    borderWidth: 0
                }
            }]
        },
        options: {
            tooltips: {
                enabled: true
            },
            legend: {
                display: true,
                position: 'right',
                labels: {
                    fontColor: getColor('--bc-color-4-a')
                }
            },
            plugins: {
                datalabels: {
                    backgroundColor: function (context) {
                        return context.dataset.backgroundColor;
                    },
                    color: getColor('--bc-color-4-a'),
                    formatter: function (value) {
                        return value + ' zł';
                    }
                }
            }
        }
    });

    EventService.addListener(Event.CHART_DATA_CHANGED, $scope, () => {
        $scope.chartData = ChartService.getData();
        $scope.selectedData = $scope.chartData[$scope.cachedChosenMonthIndex];
        $scope.updateChart();
    });

    EventService.addListener(Event.PRODUCTS_CHANGED, $scope, () => {
        ChartService.loadChartData({memberGroup: $scope.chosenMemberGroup});
    });

    $scope.updateChart = function () {
        myChart.data.labels = $scope.selectedData.labels;
        myChart.data.datasets[0].data = $scope.selectedData.values;
        myChart.update();
    };

    ChartService.loadChartData({memberGroup: $scope.chosenMemberGroup});
});