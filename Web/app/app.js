'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'myAppServices',
  'myApp.designer',
  'myApp.mobile',
  'myApp.version'
]).

config(['$routeProvider', function($routeProvider) {
	if($(document).width() > 480)
  	$routeProvider.otherwise({redirectTo: '/designer'});
 	else
 		$routeProvider.otherwise({redirectTo: '/mobile'});
}]);
