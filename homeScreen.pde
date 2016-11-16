class homeScreen {
  
  Button b_arcGraph;
  Button b_about;
  
  
  PImage logo; 
  PFont font;
  
  homeScreen() {
    b_arcGraph = new Button(3*width/4+ (height/6), (height/3)-25, height/3, height/3, color(100,100,100), 
                            "Graph", 72, loadFont("AppleGothic-72.vlw"));
    //about = new Button(3*width/4+ (height/6), (2*height/3) + 25, height/3, height/3, color(100,100,100), 
                            //"About", 72, loadFont("AppleGothic-72.vlw")); 
    //text = new Button(width/4 - (height/6), (height/3)-25, height/3, height/3, color(100,100,100), 
                            //"Sources", 72, loadFont("AppleGothic-72.vlw"));
    b_about = new Button(width/4 - (height/6), (2*height/3) + 25, height/3, height/3, color(100,100,100), 
                            "About", 72, loadFont("AppleGothic-72.vlw"));                           
    logo = loadImage("logoresize.png");
    font = loadFont("CopperplateGothic-Bold-48.vlw");
  }
  
  void draw() {  
    background(73,242,181);
    pushMatrix();
    pushStyle();
    translate(width/2, height/2);
    stroke(0);
    strokeWeight(12);
    fill(217,222,220);
    ellipse(0,0,500,500);
    imageMode(CENTER);
    image(logo, 0,0);
    fill(0);
    textFont(font, 70);
    text("Canon Compare", -textWidth("Canon Compare")/2, -280);
    popMatrix();
    rectMode(CENTER);
    noStroke();
    b_arcGraph.draw();
    b_about.draw();
    popStyle();

  }
  
  void whenPressed() {
    if (b_arcGraph.isClicked()) state = STATE_ARCGRAPH;
    else if (b_about.isClicked()) state = STATE_ABOUT;
  }
}
