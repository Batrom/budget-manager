angular.module('ngBudgetCalc')
    .service('MemberService', function ($cookies, $rootScope, Event) {
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
            $rootScope.$emit(Event.MEMBER_CHANGED);
        };

        this.clearData = () => {
            this.remove();
        }
    });