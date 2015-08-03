'use strict';

/* Services */

var myAppServices = angular.module('myAppServices', ['ngResource']);

var KEY = "93a381c670834a0897841762c1bb270a";
var SECRET = "d572f804da92417fb94dbac0097f0cac";

var WEBFONT_KEY = "AIzaSyCPPgxBAlE1i5wSs_UCiHdSzTRbUnFgahE"

myAppServices.factory('Symbol', ['$resource',
  function($resource){
    return $resource('assets/symbols/:symbolId.json', {}, {
      query: {method:'GET', params:{symbolId:'symbols'}, isArray:true}
    });
  }]);

myAppServices.factory('Font', ['$resource',
  function($resource){
    return $resource('https://www.googleapis.com/webfonts/v1/webfonts?sort=popularity&key=' + WEBFONT_KEY, {}, {
      query: {
        method:'GET',
        isArray: true,
        limit: 100,
        transformResponse: function (data) {
          return angular.fromJson(data).items.slice(0,100);
        }}
    });
  }]);
