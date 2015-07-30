function SymbolElement(canvas) {
  this.e = "";
  this.canvas = canvas;
  this.scaleTo = canvas.height - 250;
  this.left = canvas.width / 2 - 130;
  this.top = canvas.height / 2 - 150;
  this.fillColor = "rgb(0,0,0)";
  this.bordercolor = "";
  this.borderwidth = "";
}

function TextElement(canvas) {
  this.e = "";
  this.canvas = canvas;
  this.content = "Company Name"
  this.left = canvas.width / 2  - 195;
  this.top = canvas.height - 100;
  this.width = "";
  this.height = "";
  this.textColor = "rgb(0,0,0)";
  this.font = { "value": "Open Sans", "weight": "400", "style": "normal"};
}