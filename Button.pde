class Button {
  
  int x;
  int y;
  int l;
  int w;
  color c;
  String s;
  int tSize;
  PFont bFont;
  
  Button() {
    this(width / 2, height / 2, "Enter");   
  }
  
  Button(int x, int y, String s) {
    this(x, y, color(100, 100, 100, 100), s);
  }
  
  Button(int x, int y, int l, int w, color c, String s) {
    this(x, y, l, w, c, s, 32, loadFont("AppleGothic-24.vlw"));
  }
  
  Button(int x, int y, color c, String s) {
    this(x, y, 160, 65, c, s, 32, loadFont("AppleGothic-24.vlw"));
  }
  
  Button(int x, int y, int l, int w, color c, String s, int tSize) {
    this(x, y, l, w, c, s, tSize, loadFont("AppleGothic-24.vlw"));
  }
  
  Button(int x, int y, int l, int w, color c, String s, int tSize, PFont bFont) {
    this.x = x; 
    this.y = y;
    this.l = l;
    this.w = w;
    this.c = c;
    this.s = s;
    this.tSize = tSize;
    this.bFont = bFont;
    
  }
 
  
  boolean isClicked() {
    println(x, y);
    if ((mouseX >= x - l / 2 && mouseX <= (x + l / 2)) && 
        (mouseY >= y - w / 2 && mouseY <= (y + w / 2))) {
       return true;  
        
    } else {
      return false;
    }   
  }
  
  void draw() {
    pushStyle();
    fill(c);
    rectMode(CENTER);
    rect(x, y, l, w);
    textAlign(CENTER, CENTER);
    textFont(bFont, tSize);
    fill(255);
    text(s, x, y, l, w);
    popStyle();
  }
  
}
