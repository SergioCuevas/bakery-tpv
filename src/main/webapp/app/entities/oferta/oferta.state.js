(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('oferta', {
            parent: 'entity',
            url: '/oferta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bakeryTpvApp.oferta.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/oferta/ofertas.html',
                    controller: 'OfertaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('oferta');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('oferta-detail', {
            parent: 'oferta',
            url: '/oferta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bakeryTpvApp.oferta.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/oferta/oferta-detail.html',
                    controller: 'OfertaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('oferta');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Oferta', function($stateParams, Oferta) {
                    return Oferta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'oferta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('oferta-detail.edit', {
            parent: 'oferta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oferta/oferta-dialog.html',
                    controller: 'OfertaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Oferta', function(Oferta) {
                            return Oferta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('oferta.new', {
            parent: 'oferta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oferta/oferta-dialog.html',
                    controller: 'OfertaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                imagen: null,
                                imagenContentType: null,
                                descripcion: null,
                                precio: null,
                                fechainicio: null,
                                fechafinal: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('oferta', null, { reload: 'oferta' });
                }, function() {
                    $state.go('oferta');
                });
            }]
        })
        .state('oferta.edit', {
            parent: 'oferta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oferta/oferta-dialog.html',
                    controller: 'OfertaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Oferta', function(Oferta) {
                            return Oferta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('oferta', null, { reload: 'oferta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('oferta.delete', {
            parent: 'oferta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oferta/oferta-delete-dialog.html',
                    controller: 'OfertaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Oferta', function(Oferta) {
                            return Oferta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('oferta', null, { reload: 'oferta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
