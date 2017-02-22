(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('LineaOfertaController', LineaOfertaController);

    LineaOfertaController.$inject = ['LineaOferta'];

    function LineaOfertaController(LineaOferta) {

        var vm = this;

        vm.lineaOfertas = [];

        loadAll();

        function loadAll() {
            LineaOferta.query(function(result) {
                vm.lineaOfertas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
