(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('OfertaDeleteController',OfertaDeleteController);

    OfertaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Oferta'];

    function OfertaDeleteController($uibModalInstance, entity, Oferta) {
        var vm = this;

        vm.oferta = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Oferta.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
