angular.module('ngBudgetCalc')
    .service('initDataService', function (initDataFactory, restService, event) {
        let memberGroups = [];
        let productCategories = [];
        let loadInitDataSuccess = function (response) {
            return restService.fetchData(() => {
                    memberGroups = response.data.memberGroups;
                    productCategories = response.data.productCategories;
                }, event.GET_INIT_DATA
            );
        };

        this.getMemberGroups = () => memberGroups;
        this.getProductsCategories = () => productCategories;
        this.loadInitData = initDataFactory.getInitData(loadInitDataSuccess);
    });