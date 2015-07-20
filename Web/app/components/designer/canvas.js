function Symboll(canvas) {
  this.e = "";
  this.canvas = canvas;
  this.scaleTo = canvas.height / 3;
  this.left = canvas.width/2.5;
  this.top = canvas.height/2.5;
  this.fillColor = "rgb(0,0,0)";
  this.bordercolor = "";
  this.borderwidth = "";
}

function Text(canvas) {
  this.e = "";
  this.canvas = canvas;
  this.left = canvas.width / 3;
  this.top = canvas.height / 1.5;
  this.width = "";
  this.height = "";
  this.fillColor = "";
  this.fontFamily = "";
}