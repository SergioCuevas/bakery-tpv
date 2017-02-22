(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('TipoDetailController', TipoDetailController);

    TipoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Tipo', 'Producto', 'LineaOferta'];

    function TipoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Tipo, Producto, LineaOferta) {
        var vm = this;

        vm.tipo = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bakeryTpvApp:tipoUpdate', function(event, result) {
            vm.tipo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
