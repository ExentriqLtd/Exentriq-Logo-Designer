'use strict';

/* Services */

var myAppServices = angular.module('myAppServices', ['ngResource']);

myAppServices.factory('Symbol', ['$resource',
  function($resource){
    return $resource('symbols/:symbolId.json', {}, {
      query: {method:'GET', params:{symbolId:'symbols'}, isArray:true}
    });
  }]);
