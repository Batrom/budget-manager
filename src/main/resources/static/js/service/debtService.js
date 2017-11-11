angular.module('ngBudgetCalc')
    .service('debtService', function (debtFactory) {
        let debts = [];
        const months = ["styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"];

        this.getDebts = function () {
          return debts;
        };

        this.loadDebts = function (user) {
            debtFactory.getDebts(user)
                .then(
                    function (response) {
                        fetchLoadDebtsResponse(response);
                    },
                    function (error) {
                        console.error(error);
                    });
        };

        this.getDebtText = function(debt) {
            let formattedDebt = Number(Math.abs(debt.debt)).toFixed(2).toString().replace('.', ',') + ' zł';
            if (debt.debt > 0) {
                return 'Masz do oddania ' + formattedDebt + ' użytkownikowi ' + debt.otherUser;
            } else if (debt.debt < 0) {
                return 'Użytkownik ' + debt.otherUser + ' powinien Ci oddać ' + formattedDebt;
            }
        };

        let fetchLoadDebtsResponse = function (response) {
            debts = response.data.sort((a, b) => {
                a = a.creationDate.split('.').reverse().join('');
                b = b.creationDate.split('.').reverse().join('');
                return a > b ? -1 : a < b ? 1 : 0;
            }).map(debt => {
                let debtArray = debt.creationDate.split('.');
                debt.creationDate = months[debtArray[0] - 1] + ' ' + debtArray[1];
                return debt;
            });
            console.log(debts);
        };
    });