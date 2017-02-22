(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('ProductoDetailController', ProductoDetailController);

    ProductoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Producto', 'Tipo', 'LineaOferta', 'Ticket'];

    function ProductoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Producto, Tipo, LineaOferta, Ticket) {
        var vm = this;

        vm.producto = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bakeryTpvApp:productoUpdate', function(event, result) {
            vm.producto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
