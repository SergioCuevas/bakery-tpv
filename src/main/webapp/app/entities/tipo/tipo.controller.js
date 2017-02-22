(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('TipoController', TipoController);

    TipoController.$inject = ['DataUtils', 'Tipo'];

    function TipoController(DataUtils, Tipo) {

        var vm = this;

        vm.tipos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Tipo.query(function(result) {
                vm.tipos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
