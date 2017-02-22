(function() {
    'use strict';
    angular
        .module('bakeryTpvApp')
        .factory('Ticket', Ticket);

    Ticket.$inject = ['$resource', 'DateUtils'];

    function Ticket ($resource, DateUtils) {
        var resourceUrl =  'api/tickets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecha = DateUtils.convertDateTimeFromServer(data.fecha);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
