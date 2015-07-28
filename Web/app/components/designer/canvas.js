function SymbolElement(canvas) {
  this.e = "";
  this.canvas = canvas;
  this.scaleTo = canvas.height / 3;
  this.left = canvas.width / 2.5;
  this.top = canvas.height / 3;
  this.fillColor = "rgb(0,0,0)";
  this.bordercolor = "";
  this.borderwidth = "";
}

function TextElement(canvas) {
  this.e = "";
  this.canvas = canvas;
  this.left = canvas.width / 3;
  this.top = canvas.height - 100;
  this.width = "";
  this.height = "";
  this.textColor = "rgb(0,0,0)";
  this.font = { "value": "Open Sans", "weight": "400", "style": "normal"};
}