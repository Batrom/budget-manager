angular.module('ngBudgetCalc')
    .controller('updateProductModalController', function($scope, product, memberGroups, productService, debtService, $uibModalInstance) {
        $scope.product = angular.copy(product);
        $scope.memberGroups = memberGroups;

        $scope.saveChanges = function () {
            productService.updateProduct($scope.product);
            $uibModalInstance.close($scope.product);
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });
