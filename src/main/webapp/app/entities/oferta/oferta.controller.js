(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('OfertaController', OfertaController);

    OfertaController.$inject = ['DataUtils', 'Oferta'];

    function OfertaController(DataUtils, Oferta) {

        var vm = this;

        vm.ofertas = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Oferta.query(function(result) {
                vm.ofertas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
