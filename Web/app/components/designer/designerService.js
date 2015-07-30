'use strict';

/* Services */

var myAppServices = angular.module('myAppServices', ['ngResource']);

var KEY = "93a381c670834a0897841762c1bb270a"
var SECRET = "d572f804da92417fb94dbac0097f0cac"

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
