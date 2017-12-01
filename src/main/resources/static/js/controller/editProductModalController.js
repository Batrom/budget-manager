angular.module('ngBudgetCalc')
    .controller('editProductModalController', function($scope, product, productService, debtService, $uibModalInstance) {
        $scope.product = product;

        $scope.saveChanges = function () {
            productService.editProduct($scope.product);
            $uibModalInstance.close();
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });
