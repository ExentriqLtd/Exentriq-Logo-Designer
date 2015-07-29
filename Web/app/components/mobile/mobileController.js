'use strict';

angular.module('myApp.mobile', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/mobile', {
    templateUrl: 'app/components/mobile/mobileView.html',
    controller: 'MobileCtrl'
  });
}])

.controller('MobileCtrl', ['$scope', '$location',
  function ($scope, $location) {

    if($(document).width() > 480){
      $location.url('/designer');
    }

  }]);