angular.module('ngBudgetCalc')
    .service('memberService', function ($cookies, $rootScope, event) {
        let parseJSON = () => {
            let stringJSON = $cookies.get('loggedMember');
            return stringJSON ? JSON.parse(stringJSON) : {name: ''};
        };
        this.getMember = () => parseJSON();

        this.remove = () => {
            $cookies.remove('loggedMember');
        };

        this.setMember = memberName => {
            $cookies.put('loggedMember', JSON.stringify({
                name: memberName
            }));
            $rootScope.$emit(event.MEMBER_CHANGED);
        }
    });