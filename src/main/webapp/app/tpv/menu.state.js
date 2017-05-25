(function() {
    'use strict';

    angular
        .module('bakeryTpvApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('shop', {
            parent: 'app',
            url: '/menu',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'menutipos@': {
                    templateUrl: 'app/tables/listatickets/listatickets.html',
                    controller: '',
                    controllerAs: 'vm'
                },
                'listaproductos@': {
                    templateUrl: 'app/tables/listaproductos/listaproductos.html',
                    controller: '',
                    controllerAs: 'vm'
                },
                'listatickets@': {
                    templateUrl: 'app/tables/menutipos/menutipos.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                },
            }
        });
    }
})();
