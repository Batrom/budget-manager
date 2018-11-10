angular.module('ngBudgetCalc').controller('ChartController', function ($scope, ChartService, Event, MemberService, EventService) {
    $scope.chartData = [{labels: [''], values: []}];
    $scope.selectedData = $scope.chartData[0];

    $scope.getSelectSize = () => $scope.chartData.length !== 0 ? $scope.chartData.length : 1;

    let myChart = new Chart(angular.element(document.querySelector('#chart'))[0], {
        type: 'doughnut',
        data: {
            labels: $scope.selectedData.labels,
            datasets: [{
                label: '# of Votes',
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
                borderWidth: 1
            }]
        },
        options: {
            legend: {
                display: true,
                position: 'right',
                labels: {
                    fontColor: '#f5f5f5'
                }
            }
        }
    });

    EventService.addListener(Event.CHART_DATA_CHANGED, $scope, () => {
        $scope.chartData = ChartService.getData();
        $scope.selectedData = $scope.chartData[0];
        $scope.updateChart();
    });

    EventService.addListener(Event.PRODUCTS_CHANGED, $scope, () => {
        ChartService.loadChartData({member: MemberService.getMember().name});
    });

    $scope.updateChart = function () {
        myChart.data.labels = $scope.selectedData.labels;
        myChart.data.datasets[0].data = $scope.selectedData.values;
        myChart.update();
    };

    ChartService.loadChartData({member: MemberService.getMember().name});
});