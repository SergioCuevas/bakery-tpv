'use strict';

describe('Controller Tests', function() {

    describe('LineaOferta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLineaOferta, MockOferta, MockProducto, MockTipo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLineaOferta = jasmine.createSpy('MockLineaOferta');
            MockOferta = jasmine.createSpy('MockOferta');
            MockProducto = jasmine.createSpy('MockProducto');
            MockTipo = jasmine.createSpy('MockTipo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LineaOferta': MockLineaOferta,
                'Oferta': MockOferta,
                'Producto': MockProducto,
                'Tipo': MockTipo
            };
            createController = function() {
                $injector.get('$controller')("LineaOfertaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bakeryTpvApp:lineaOfertaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
