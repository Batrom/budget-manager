angular.module('ngBudgetCalc')
    .controller('editProductModalController', function($scope, product, productService, debtService, $rootScope, $uibModalInstance) {
        $scope.product = product;

        $scope.saveChanges = function () {
            productService.editProduct($scope.product);
            debtService.loadDebts($rootScope.loggedUser);
            $uibModalInstance.close();
        };

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    });
