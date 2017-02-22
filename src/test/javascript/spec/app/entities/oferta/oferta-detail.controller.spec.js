'use strict';

describe('Controller Tests', function() {

    describe('Oferta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOferta, MockLineaOferta, MockTicket;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOferta = jasmine.createSpy('MockOferta');
            MockLineaOferta = jasmine.createSpy('MockLineaOferta');
            MockTicket = jasmine.createSpy('MockTicket');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Oferta': MockOferta,
                'LineaOferta': MockLineaOferta,
                'Ticket': MockTicket
            };
            createController = function() {
                $injector.get('$controller')("OfertaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bakeryTpvApp:ofertaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
