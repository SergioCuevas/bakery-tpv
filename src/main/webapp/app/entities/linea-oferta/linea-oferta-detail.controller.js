(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .controller('LineaOfertaDetailController', LineaOfertaDetailController);

    LineaOfertaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LineaOferta', 'Oferta', 'Producto', 'Tipo'];

    function LineaOfertaDetailController($scope, $rootScope, $stateParams, previousState, entity, LineaOferta, Oferta, Producto, Tipo) {
        var vm = this;

        vm.lineaOferta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bakeryTpvApp:lineaOfertaUpdate', function(event, result) {
            vm.lineaOferta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
