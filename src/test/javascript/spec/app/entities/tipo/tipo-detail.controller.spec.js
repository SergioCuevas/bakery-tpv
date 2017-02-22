'use strict';

describe('Controller Tests', function() {

    describe('Tipo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTipo, MockProducto, MockLineaOferta;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTipo = jasmine.createSpy('MockTipo');
            MockProducto = jasmine.createSpy('MockProducto');
            MockLineaOferta = jasmine.createSpy('MockLineaOferta');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tipo': MockTipo,
                'Producto': MockProducto,
                'LineaOferta': MockLineaOferta
            };
            createController = function() {
                $injector.get('$controller')("TipoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bakeryTpvApp:tipoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
