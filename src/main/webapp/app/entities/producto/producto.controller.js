(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('ProductoController', ProductoController);

    ProductoController.$inject = ['DataUtils', 'Producto'];

    function ProductoController(DataUtils, Producto) {

        var vm = this;

        vm.productos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Producto.query(function(result) {
                vm.productos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
