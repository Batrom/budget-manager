angular.module('ngBudgetCalc')
    .service('DebtService', function (DebtFactory, RestService, Event) {
        let debts = [];
        const months = ["styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"];

        let loadDebtsCallback = function (response) {
            return RestService.fetchData(() => {
                debts = response.data.sort((a, b) => {
                    a = a.creationDate.split('.').reverse().join('');
                    b = b.creationDate.split('.').reverse().join('');
                    return a > b ? -1 : a < b ? 1 : 0;
                }).map(debt => {
                    let debtArray = debt.creationDate.split('.');
                    debt.creationDate = months[debtArray[0] - 1] + ' ' + debtArray[1];
                    return debt;
                });
            }, Event.DEBTS_CHANGED)
        };

        this.loadDebts = DebtFactory.getDebts(loadDebtsCallback);
        this.getDebts = () => debts;
        this.getDebtText = function (debt) {
            let formattedDebt = Number(Math.abs(debt.debt)).toFixed(2).toString().replace('.', ',') + ' zł';
            if (debt.debt > 0) {
                return 'Masz do oddania ' + formattedDebt + ' użytkownikowi ' + debt.otherMember;
            } else if (debt.debt < 0) {
                return 'Użytkownik ' + debt.otherMember + ' powinien Ci oddać <b>' + formattedDebt + '</b>';
            }
        };

        this.clearData = () => {
            debts = [];
        }
    });