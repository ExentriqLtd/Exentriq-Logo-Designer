angular.module('starter.controllers', [])

    .controller('DesignCtrl', function($scope) {
        $scope.greeting = function () {
            $scope.hello = 'Hello World';
        };
    })
    .controller('PreviewCtrl', function($scope, Chats) {

    })
    .controller('ExportCtrl', function($scope) {
    })

    .controller('SideMenuCtrl', function($scope, $ionicSideMenuDelegate) {
        $scope.toggleLeftSideMenu = function() {
            $ionicSideMenuDelegate.toggleLeft();
        };
    });


