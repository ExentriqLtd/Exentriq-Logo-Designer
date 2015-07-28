'use strict';

/* Services */

var myAppServices = angular.module('myAppServices', ['ngResource']);

myAppServices.factory('Symbol', ['$resource',
  function($resource){
    return $resource('assets/symbols/:symbolId.json', {}, {
      query: {method:'GET', params:{symbolId:'symbols'}, isArray:true}
    });
  }]);

myAppServices.factory('Font', ['$resource',
  function($resource){
    return $resource('assets/fonts/:fontId.json', {}, {
      query: {method:'GET', params:{fontId:'fonts'}, isArray:true}
    });
  }]);
