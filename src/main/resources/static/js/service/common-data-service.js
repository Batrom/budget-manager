angular.module('ngBudgetCalc')
    .service('CommonDataService', function (CommonDataFactory, RestService, Event) {
        let memberGroups = [];
        let productCategories = [];
        let loadCommonDataCallback = function (response) {
            return RestService.fetchData(() => {
                    memberGroups = response.data.memberGroups;
                    productCategories = response.data.productCategories;
                }, Event.COMMON_DATA_CHANGED
            );
        };

        this.getMemberGroups = () => memberGroups;
        this.getProductsCategories = () => productCategories;
        this.loadCommonData = CommonDataFactory.getCommonData(loadCommonDataCallback);

        this.clearData = () => {
            memberGroups = [];
            productCategories = [];
        }
    });