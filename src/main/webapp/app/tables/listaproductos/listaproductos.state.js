(function () {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('listaproducttos', {
            parent: 'listaproductos',
            url: '/menu',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'menutipos@': {
                    templateUrl: 'app/tables/listatickets/listatickets.html',
                    controller: '',
                    controllerAs: 'vm'
                }
            }, resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {

                    return $translate.refresh();
                }]
            }
        });
    }
})();
