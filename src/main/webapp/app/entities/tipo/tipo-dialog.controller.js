(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('TipoDialogController', TipoDialogController);

    TipoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Tipo', 'Producto', 'LineaOferta'];

    function TipoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Tipo, Producto, LineaOferta) {
        var vm = this;

        vm.tipo = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.productos = Producto.query();
        vm.lineaofertas = LineaOferta.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipo.id !== null) {
                Tipo.update(vm.tipo, onSaveSuccess, onSaveError);
            } else {
                Tipo.save(vm.tipo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bakeryTpvApp:tipoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImage = function ($file, tipo) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        tipo.image = base64Data;
                        tipo.imageContentType = $file.type;
                    });
                });
            }
        };

    }
})();
