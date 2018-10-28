angular.module('ngBudgetCalc').constant('url', {
        getCommonData: '/common/getData',

        getDebts: '/debt/getAll?member=',

        getChartData: '/chart/getData?member=',

        getDuties: '/duty/getAll',
        saveDuty: '/duty/save',
        deleteDuty: '/duty/delete',
        updateDuty: '/duty/update',

        getProducts: '/product/getAll',
        saveProduct: '/product/save',
        updateProduct: '/product/update',
        deleteProduct: '/product/delete',
        updateProductsCategory: '/product/updateCategory',
    }
);
