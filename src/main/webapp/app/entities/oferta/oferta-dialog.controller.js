(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('OfertaDialogController', OfertaDialogController);

    OfertaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Oferta', 'LineaOferta', 'Ticket'];

    function OfertaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Oferta, LineaOferta, Ticket) {
        var vm = this;

        vm.oferta = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.lineaofertas = LineaOferta.query();
        vm.tickets = Ticket.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.oferta.id !== null) {
                Oferta.update(vm.oferta, onSaveSuccess, onSaveError);
            } else {
                Oferta.save(vm.oferta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bakeryTpvApp:ofertaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImagen = function ($file, oferta) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        oferta.imagen = base64Data;
                        oferta.imagenContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.fechainicio = false;
        vm.datePickerOpenStatus.fechafinal = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
