(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('linea-oferta', {
            parent: 'entity',
            url: '/linea-oferta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bakeryTpvApp.lineaOferta.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/linea-oferta/linea-ofertas.html',
                    controller: 'LineaOfertaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lineaOferta');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('linea-oferta-detail', {
            parent: 'linea-oferta',
            url: '/linea-oferta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bakeryTpvApp.lineaOferta.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/linea-oferta/linea-oferta-detail.html',
                    controller: 'LineaOfertaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lineaOferta');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'LineaOferta', function($stateParams, LineaOferta) {
                    return LineaOferta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'linea-oferta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('linea-oferta-detail.edit', {
            parent: 'linea-oferta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/linea-oferta/linea-oferta-dialog.html',
                    controller: 'LineaOfertaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LineaOferta', function(LineaOferta) {
                            return LineaOferta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('linea-oferta.new', {
            parent: 'linea-oferta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/linea-oferta/linea-oferta-dialog.html',
                    controller: 'LineaOfertaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                cantidad: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('linea-oferta', null, { reload: 'linea-oferta' });
                }, function() {
                    $state.go('linea-oferta');
                });
            }]
        })
        .state('linea-oferta.edit', {
            parent: 'linea-oferta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/linea-oferta/linea-oferta-dialog.html',
                    controller: 'LineaOfertaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LineaOferta', function(LineaOferta) {
                            return LineaOferta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('linea-oferta', null, { reload: 'linea-oferta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('linea-oferta.delete', {
            parent: 'linea-oferta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/linea-oferta/linea-oferta-delete-dialog.html',
                    controller: 'LineaOfertaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LineaOferta', function(LineaOferta) {
                            return LineaOferta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('linea-oferta', null, { reload: 'linea-oferta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
