'use strict';

angular.module('myApp.designer', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/designer', {
    templateUrl: 'app/components/designer/designerView.html',
    controller: 'DesignerCtrl'
  });
}])

.controller('DesignerCtrl', ['$scope', '$location', 'Symbol', 'Font',
  function($scope, $location, Symbol, Font) {

    if($(document).width() < 480){
      $location.url('/mobile');
    }

    // ----------------------init canvas and its elements------------------------
    
    var canvas = new fabric.Canvas('c');
    canvas.setWidth($('.canvas-container').parent().width());
    canvas.setHeight(450);

    $scope.symbol = new SymbolElement(canvas);
    $scope.text = new TextElement(canvas);

    $scope.symbols = Symbol.query(function(symbols){
      $scope.setLogoSymbol(symbols[0].svgPath);
    });


    $scope.fonts = Font.query(function(fonts){
      $scope.text.font = fonts[0];
      $scope.text.font.style = fonts[0].styles[0].value;
      $scope.text.font.weight = fonts[0].weights[0].value;
    });

    setCanvasListener(canvas, $scope);

    // ---------------------draw symbol in canvas-----------------------------
    $scope.setLogoSymbol = function(svgPath) {
      var oldSymbol = $scope.symbol;
      $.get(svgPath, function(svgString){
          var path = fabric.loadSVGFromString(svgString, function(objects, options) {
          var obj = fabric.util.groupSVGElements(objects, options);
          obj.scaleToHeight(oldSymbol.scaleTo)
            .set({ left: oldSymbol.left, top: oldSymbol.top })
            .setCoords();

          $scope.refreshCanvasForSvg(obj);
        });
      }, 'text');
    };

    $scope.refreshCanvasForSvg = function(obj){
      canvas.discardActiveGroup();
      canvas.remove($scope.symbol.e);
      $scope.symbol.e = obj;
      for (var i = 0; i < $scope.symbol.e.paths.length; i++) {
        $scope.symbol.e.paths[i].setFill($scope.symbol.fillColor);
      }
      canvas.add($scope.symbol.e);
      $scope.$apply(function(){
        $scope.preview = canvas.toDataURL("image/png");
      });
    };

    // -------------------------draw text in canvas-------------------------------
    $scope.setLogoName = function(){
      var textCanvas = $scope.text;
      var oldText = textCanvas.e;
      var text = new fabric.Text($scope.name, { left: textCanvas.left, top: textCanvas.top });
      text.setFontFamily($scope.text.font.value);
      text.setFontStyle($scope.text.font.style);
      text.setFontWeight($scope.text.font.weight);
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
      var symbolCanvas = $scope.symbol;
      for (var i = 0; i < activeObject.paths.length; i++) {
        activeObject.paths[i].setFill($('#fillcolorpicker').val());
      }
      symbolCanvas.fillColor = $('#fillcolorpicker').val();
      canvas.renderAll();
      canvas.discardActiveObject();
      $scope.preview = canvas.toDataURL("image/png");
      canvas.setActiveObject(activeObject);
    };

    // Text properties change functions------------------------------------------

    $scope.changeTextColor = function(){
      var activeObject = canvas.getActiveObject();
      activeObject.setFill($('#textcolorpicker').val());
      canvas.renderAll();
      $scope.preview = canvas.toDataURL("image/png");
    };

    $scope.changeTextFont = function(){
      var activeObject = canvas.getActiveObject();
      var font = $scope.text.font;
      if(font != undefined)activeObject.setFontFamily(font.value);
      if(font.style != undefined) activeObject.setFontStyle(font.style);
      if(font.weight != undefined) activeObject.setFontWeight(font.weight);
      font.style = font.styles[0].value;
      font.weight = font.weights[0].value;
      canvas.renderAll();
    };

    $scope.saveLogo = function () {
      canvas.discardActiveObject();
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
        canvas.discardActiveObject();
        $scope.preview = canvas.toDataURL("image/png");
        canvas.setActiveObject(activeObject);
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

  canvas.on({'object:modified': function(e) {
    var activeObject = e.target;
    if(activeObject.get('type') == 'text'){
      
    }else if (activeObject.get('type') == 'path-group'){
      $scope.$apply(function(){
        canvas.discardActiveObject();
        $scope.preview = canvas.toDataURL("image/png");
        canvas.setActiveObject(activeObject);
      });
      $scope.symbol.scaleTo = activeObject.width * activeObject.scaleX;
      $scope.symbol.left = activeObject.left;
      $scope.symbol.top = activeObject.top;
    }
   }
  });

};