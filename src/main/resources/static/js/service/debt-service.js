angular.module('ngBudgetCalc')
    .service('debtService', function (debtFactory, restService, event) {
        let debts = [];
        const months = ["styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"];

        let loadDebtsSuccess = function(response) {
            return restService.fetchData(() => {
                debts = response.data.sort((a, b) => {
                    a = a.creationDate.split('.').reverse().join('');
                    b = b.creationDate.split('.').reverse().join('');
                    return a > b ? -1 : a < b ? 1 : 0;
                }).map(debt => {
                    let debtArray = debt.creationDate.split('.');
                    debt.creationDate = months[debtArray[0] - 1] + ' ' + debtArray[1];
                    return debt;
                });
            }, event.GET_DEBTS)
        };

        this.loadDebts = debtFactory.getDebts(loadDebtsSuccess);
        this.getDebts = () => debts;
        this.getDebtText = function (debt) {
            let formattedDebt = Number(Math.abs(debt.debt)).toFixed(2).toString().replace('.', ',') + ' zł';
            if (debt.debt > 0) {
                return 'Masz do oddania ' + formattedDebt + ' użytkownikowi ' + debt.otherMember;
            } else if (debt.debt < 0) {
                return 'Użytkownik ' + debt.otherMember + ' powinien Ci oddać ' + formattedDebt;
            }
        };
    });