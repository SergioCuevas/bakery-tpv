(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('TicketDetailController', TicketDetailController);

    TicketDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ticket', 'User', 'Oferta', 'Producto'];

    function TicketDetailController($scope, $rootScope, $stateParams, previousState, entity, Ticket, User, Oferta, Producto) {
        var vm = this;

        vm.ticket = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bakeryTpvApp:ticketUpdate', function(event, result) {
            vm.ticket = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
