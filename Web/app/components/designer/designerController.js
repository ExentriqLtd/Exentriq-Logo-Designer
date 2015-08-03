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

    setTimeout(function(){
      fillImage();
    }, 200);

    $scope.symbols = Symbol.query(function(symbols){
      $scope.setLogoSymbol(symbols[0].svgPath);
    });

    $scope.fonts = Font.query(function(fonts){
      $scope.text.font = fonts[0];
      $scope.text.font.variant = fonts[0].variants[0];
      var families = [];
      fonts.map(function(obj){ 
        families.push(obj.family); 
      });
      WebFont.load({
        google: {
          families: families
        }
      });
      $scope.setLogoName($scope.text.content);
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
    $scope.setLogoName = function(content){
      $scope.name = content;
      var textCanvas = $scope.text;
      var oldText = textCanvas.e;
      var text = new fabric.Text(content, { fontFamily: $scope.text.font.family, left: textCanvas.left, top: textCanvas.top, textAlign: "center" });
      textCanvas.e = text;
      textCanvas.content = content;
      $scope.refreshCanvasForText(oldText);
      //text.setFontVariant($scope.text.font.style);
      
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
      canvas.discardActiveObject();
      $scope.preview = canvas.toDataURL("image/png");
      canvas.discardActiveObject();
      $scope.preview = canvas.toDataURL("image/png");
      canvas.setActiveObject(activeObject);
    };

    $scope.changeTextFontFamily = function(){
      var activeObject = canvas.getActiveObject();
      var font = $scope.text.font;
      $scope.text.font.variant = font.variants[0];
      activeObject.setFontFamily(font.family);
      canvas.renderAll();
      canvas.discardActiveObject();
      $scope.preview = canvas.toDataURL("image/png");
      canvas.setActiveObject(activeObject);
    };

    $scope.changeTextFontVariant = function(){
      var activeObject = canvas.getActiveObject();
      var style = $scope.text.font.variant.replace(/[^a-zA-Z]+/g, '');
      var weight = $scope.text.font.variant.replace(/[^0-9]+/g, '');
      if(weight != "" && weight!= " ") activeObject.setFontWeight(weight);
      if(style == "italic") activeObject.setFontStyle(style);
      if(style == "regular"){
        activeObject.setFontStyle();
        activeObject.setFontWeight();
        activeObject.setFontFamily($scope.text.font.family);
      }
      canvas.renderAll();
      canvas.discardActiveObject();
      $scope.preview = canvas.toDataURL("image/png");
      canvas.setActiveObject(activeObject);
    };

    $scope.saveLogo = function () {
      canvas.discardActiveObject();
      saveToDisk(canvas, "logo.png");
    };

    $scope.refreshPreview = function() {
      $scope.$apply(function(){
        var activeObject = canvas.getActiveObject();
        if(canvas.getActiveGroup()){
          canvas.discardActiveGroup();
          $scope.preview = canvas.toDataURL("image/png");
        }
        if(canvas.getActiveObject()){
          canvas.discardActiveObject();
         $scope.preview = canvas.toDataURL("image/png");
        canvas.setActiveObject(activeObject);
        } 
      });
    }

  }]);

// ----------------------------------utils-----------------------------------

function setCanvasListener(canvas, $scope){
  $('.form-control').prop('disabled', true);
  canvas.on({'object:moving': function(e) {
      var activeObject = e.target;
      var symbol = $scope.symbol;
      var text = $scope.text;
      if(activeObject.get('type') == 'path-group'){
        symbol.left = activeObject.get('left');
        symbol.top = activeObject.get('top');
        $scope.refreshPreview();
      }else if (activeObject.get('type') == 'text'){
        text.left = activeObject.get('left');
        text.top = activeObject.get('top');
        $scope.refreshPreview();
      }else{
        $scope.$apply(function(){
          $scope.preview = canvas.toDataURL("image/png");
        });
      }
    }
  });

  canvas.on({'object:selected': function(e) {
    var activeObject = e.target;
    if(activeObject.get('type') == 'text'){
      $('.controls').hide();
      $('#controls-text').show();
      $('#controls-text .form-control').prop('disabled', false);
      $("#textcolorpicker").spectrum("enable");
    }else if (activeObject.get('type') == 'path-group'){
      $('.controls').hide();
     $('#controls-symbol').show();
      $('#controls-symbol .form-control').prop('disabled', false);
      $("#fillcolorpicker").spectrum("enable");
    }
   }
  });

  canvas.on({'selection:cleared': function(e) {
     $('.form-control').prop('disabled', true);
     $("#textcolorpicker").spectrum("disable");
     $("#fillcolorpicker").spectrum("disable");
   }
  });

  canvas.on({'object:modified': function(e) {
    var activeObject = e.target;
    $scope.refreshPreview();
    if(activeObject.get('type') == 'text'){

    }else if (activeObject.get('type') == 'path-group'){
      $scope.symbol.scaleTo = activeObject.width * activeObject.scaleX;
      $scope.symbol.left = activeObject.left;
      $scope.symbol.top = activeObject.top;
    }
   }
  });



};