(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('OfertaDetailController', OfertaDetailController);

    OfertaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Oferta', 'LineaOferta', 'Ticket'];

    function OfertaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Oferta, LineaOferta, Ticket) {
        var vm = this;

        vm.oferta = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bakeryTpvApp:ofertaUpdate', function(event, result) {
            vm.oferta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
