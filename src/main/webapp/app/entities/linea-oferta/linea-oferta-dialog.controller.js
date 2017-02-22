(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('LineaOfertaDialogController', LineaOfertaDialogController);

    LineaOfertaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LineaOferta', 'Oferta', 'Producto', 'Tipo'];

    function LineaOfertaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LineaOferta, Oferta, Producto, Tipo) {
        var vm = this;

        vm.lineaOferta = entity;
        vm.clear = clear;
        vm.save = save;
        vm.ofertas = Oferta.query();
        vm.productos = Producto.query();
        vm.tipos = Tipo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lineaOferta.id !== null) {
                LineaOferta.update(vm.lineaOferta, onSaveSuccess, onSaveError);
            } else {
                LineaOferta.save(vm.lineaOferta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bakeryTpvApp:lineaOfertaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
