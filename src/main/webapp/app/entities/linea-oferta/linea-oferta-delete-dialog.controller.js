(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('LineaOfertaDeleteController',LineaOfertaDeleteController);

    LineaOfertaDeleteController.$inject = ['$uibModalInstance', 'entity', 'LineaOferta'];

    function LineaOfertaDeleteController($uibModalInstance, entity, LineaOferta) {
        var vm = this;

        vm.lineaOferta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LineaOferta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
