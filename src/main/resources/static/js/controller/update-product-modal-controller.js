angular.module('ngBudgetCalc')
    .controller('UpdateProductModalController', function($scope, product, memberGroups, ProductService, DebtService, $uibModalInstance) {
        $scope.product = angular.copy(product);
        $scope.memberGroups = memberGroups;

        $scope.saveChanges = function () {
            ProductService.updateProduct($scope.product);
            $uibModalInstance.close($scope.product);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });
