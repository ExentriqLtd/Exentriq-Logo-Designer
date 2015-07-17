'use strict';

angular.module('myApp.designer', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/designer', {
    templateUrl: 'app/components/designer/designerView.html',
    controller: 'DesignerCtrl'
  });
}])

.controller('DesignerCtrl', ['$scope', 'Symbol',
  function($scope, Symbol) {
    // init canvas and its elements--------------------------------------------
    var canvas = new fabric.Canvas('c');
    canvas.setWidth(800);
    canvas.setHeight(500);

    $scope.symbol = new Symboll(canvas);
    $scope.text = new Text(canvas);

    $scope.symbols = Symbol.query(function(symbols){
      $scope.setLogoSymbol(symbols[0].svgPath);
    });

    setCanvasListener(canvas, $scope);

    // draw symbol in canvas---------------------------------------------------
    $scope.setLogoSymbol = function(svgPath) {
      var symbolCanvas = $scope.symbol;
      var oldSymbol = symbolCanvas.e;
      $.get(svgPath, function(svgString){
          var path = fabric.loadSVGFromString(svgString, function(objects, options) {
          var obj = fabric.util.groupSVGElements(objects, options);
          obj.scaleToHeight(canvas.height / 3)
            .set({ left: symbolCanvas.left, top: symbolCanvas.top })
            .setCoords();
          symbolCanvas.e = obj;
          
          $scope.refreshCanvasForSvg(oldSymbol);
        });
      }, 'text');
    };

    $scope.refreshCanvasForSvg = function(oldSymbol){
      canvas.remove(oldSymbol);
      canvas.add($scope.symbol.e);
      canvas.renderAll()
    };

    // draw text in canvas-----------------------------------------------------
    $scope.setLogoName = function(){
      var textCanvas = $scope.text;
      var oldText = textCanvas.e;
      var text = new fabric.Text($scope.name, { left: textCanvas.left, top: textCanvas.top });
      textCanvas.e = text;
      $scope.refreshCanvasForText(oldText);
    };

    $scope.refreshCanvasForText = function(oldText){
      canvas.remove(oldText);
      canvas.add($scope.text.e);
      $scope.preview = canvas.toDataURL("image/png");
    };

    // Symbol properties change functions---------------------------------------

    $scope.changeFillColor = function(){
      var activeObject = canvas.getActiveObject();
      for (var i = 0; i < activeObject.paths.length; i++) {
        activeObject.paths[i].setFill($('#fillcolorpicker').val());
      }
      canvas.renderAll();
    };

    $scope.changeBorderColor = function(){
      var activeObject = canvas.getActiveObject();
      for (var i = 0; i < activeObject.paths.length; i++) {
          activeObject.paths[i].setStroke($('#bordercolorpicker').val());
      }
      canvas.renderAll();
    };

    $scope.changeBorderWidth = function(){
      var activeObject = canvas.getActiveObject();
      for (var i = 0; i < activeObject.paths.length; i++) {
        activeObject.paths[i].setStrokeWidth(1);
      }
      canvas.renderAll();
    };

    // Symbol properties change functions------------------------------------------

    $scope.changeTextColor = function(){
      var activeObject = canvas.getActiveObject();
      activeObject.setFill($('#textcolorpicker').val());
      canvas.renderAll();
    };

    $scope.changeTextFontFamily = function(){
      var activeObject = canvas.getActiveObject();
      activeObject.setFontFamily($('#textfontselect').val());
      canvas.renderAll();
    };

    $scope.saveLogo = function () {
      saveToDisk(canvas, "logo.png");
    };

  }]);

// ----------------------------------utils-----------------------------------

function setCanvasListener(canvas, $scope){
  canvas.on({'object:moving': function(e) {
      var activeObject = e.target;
      var symbol = $scope.symbol;
      var text = $scope.text;
      if(activeObject.get('type') == 'path-group'){
        symbol.left = activeObject.get('left');
        symbol.top = activeObject.get('top');
      }else if (activeObject.get('type') == 'text'){
        text.left = activeObject.get('left');
        text.top = activeObject.get('top');
      }
      $scope.$apply(function(){
        $scope.preview = canvas.toDataURL("image/png");
      });
    }
  });

  canvas.on({'object:selected': function(e) {
    var activeObject = e.target;
    $('.controls').hide();
    if(activeObject.get('type') == 'text'){
      $('#controls-text').show();
    }else if (activeObject.get('type') == 'path-group'){
     $('#controls-symbol').show();
    }
   }
  });

  canvas.on({'selection:cleared': function(e) {
     $('.controls').hide();
   }
  });

};