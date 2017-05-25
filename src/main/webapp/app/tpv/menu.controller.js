(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('MenuController', MenuController);

    MenuController.$inject = ['Ticket'];

    function MenuController(Ticket) {

        var vm = this;

        vm.tickets = [];

        loadAll();


    }
})();
