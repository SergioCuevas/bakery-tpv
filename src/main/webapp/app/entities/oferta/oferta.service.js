(function() {
    'use strict';
    angular
        .module('bakeryTpvApp')
        .factory('Oferta', Oferta);

    Oferta.$inject = ['$resource', 'DateUtils'];

    function Oferta ($resource, DateUtils) {
        var resourceUrl =  'api/ofertas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechainicio = DateUtils.convertDateTimeFromServer(data.fechainicio);
                        data.fechafinal = DateUtils.convertDateTimeFromServer(data.fechafinal);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
