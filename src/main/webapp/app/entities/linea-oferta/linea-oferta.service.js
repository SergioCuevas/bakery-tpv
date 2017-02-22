(function() {
    'use strict';
    angular
        .module('bakeryTpvApp')
        .factory('LineaOferta', LineaOferta);

    LineaOferta.$inject = ['$resource'];

    function LineaOferta ($resource) {
        var resourceUrl =  'api/linea-ofertas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
