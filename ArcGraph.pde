class ArcGraph {

  int x;
  int y;
  int initialNodeX; 
  int nodesOnScreen;
  int totalNodesOnScreen;
  int spaceBetween;
  int frequencyMax;
  int lineHeight;
  Button home;
  int mode; 
  final int MODE_POPUP = 0;
  final int MODE_GRAPH = 1;
  ArrayList<Node> nodes; 
  float sliderLast; 
  PFont font;
  Slider display;

  ArcGraph() {
    mode = MODE_GRAPH;
    home = new Button(250, 35, 120, 40, color(100, 100, 200, 200), "HOME");
    x = 0; 
    y = height-100;
    initialNodeX = 100;
    nodesOnScreen = 0;
    totalNodesOnScreen = 0;
    spaceBetween = 20;
    sliderLast = 2;
    frequencyMax = max(frequency);
    lineHeight = (height-200)/frequencyMax; 
    nodes = new ArrayList<Node>();
    for (int i = 0; i < numOfEvents; i++) {
      if (frequency[i]!=0) {
        nodesOnScreen++;
        totalNodesOnScreen++;
        nodes.add(new Node(i, initialNodeX, nodesOnScreen-1, spaceBetween, lineHeight, 2));
      }
    }
    font = loadFont("AppleGothic-24.vlw");
    cp5.setFont(font, 12);
    display = new Slider(cp5, "Display");
    display.setPosition(width-350, 20)
           .setWidth(150)
           .setHeight(30)
           .setRange(0, 2) // values can range from big to small as well
           .setValue(2)
           .setColorTickMark(0)
           .setNumberOfTickMarks(3)
           .setSliderMode(Slider.FLEXIBLE)
           ;
  }

  void draw() {
    fill(112, 206, 204);
    display.show();
    drawHeader();
    pushMatrix();
    translate(0, height-100);
    drawLines();
    noStroke();
    fill(112, 190, 190);
    rect(0, 0, width, 50);
    println(display.getTriggerEvent());
    if (display.getValue() !=  sliderLast) updateNodes(); 
    if (nodes.size()!=0) {
      for (Node r : nodes) r.draw();
    }
    popMatrix();
    drawKey();
  }

  void drawLines() {
    for (int i = 1; i <= max (frequency); i++) {
      strokeWeight(2);
      stroke(255);
      fill(255);
      line(0, -i*lineHeight, width, -i*lineHeight);
      textFont(font, 12);
      text(i, 5, -i*lineHeight-3);
    }
  }

  void drawKey() {
    for (int r = 0; r < numOfEvents; r++) {
      textFont(font, 24);
      pushMatrix();
      translate(width/2, height-20);
      fill(255);
      text("Non-canonical", -textWidth("Non-canonical gospels")-140, 0); 
      fill(112, 206, 168);
      noStroke();
      rect(-textWidth("Non-canonical gospels") - 180, -25, 30, 30);
      popMatrix();
      pushMatrix();
      fill(255);
      translate(width/2, height-20);
      text("Canonical gospels", 3*textWidth("Canonical gospels")/4-180, 0);
      fill(225, 162, 240);
      noStroke();
      rect(3*textWidth("Canonical gospels")/4 - 220, -25, 30, 30);
      popMatrix();
      pushMatrix();
      fill(255);
      translate(width/2, height-20);
      text("Both", 3*textWidth("Both")/4 + 300, 0);
      fill(88, 184, 242);
      noStroke();
      rect(3*textWidth("Both")/4 + 260, -25, 30, 30);
      popMatrix();
    }
  }

  void scroll() {
    if (spaceBetween*180*nodesOnScreen > width ) {

      for (int r = 0; r < nodesOnScreen; r++) {
        if (keyCode == LEFT) {
                                  println( nodes.get(r).x);
          nodes.get(r).x -= 5;
        } else if (keyCode == RIGHT) {

          if (nodes.get(0).x - nodes.get(0).w/2  < 0) nodes.get(r).x += 5;
        }
      }
    }
  }

  void whenPressed() {
    if (home.isClicked()) {
      display.hide();
      state = STATE_HOME;
    } else {
      if (nodes.size() != 0) {
        for (int r = 0; r < nodesOnScreen; r++) {
          if (mouseX > (nodes.get(r).x)-(nodes.get(r).w/2) &&
            mouseX < (nodes.get(r).x) + (nodes.get(r).w/2) &&
            mouseY >  height-100-(nodes.get(r).h/2)  &&
            mouseY <  height-100) 
          {
            nodes.get(r).whenPressed();
            display.hide();
          }
        }
      }
    }
  }

  void drawHeader() {
    pushStyle();
    fill(255, 255, 255);
    ellipse(width/2, 35, width, 70);
    fill(112, 206, 203);
    textFont(title, 48);
    text("Frequency of Events", width/2 - textWidth("Frequency of Events")/2, 50);
    home.draw();
    popStyle();
  }

  void updateNodes() {
    nodes.clear();
    nodesOnScreen = 0;
    sliderLast = display.getValue();
    for (int i = 0; i < numOfEvents; i++) {
      if (frequency[i]!=0) {
        if (display.getValue() == 0 && canFrequency[i] !=0) {
          nodesOnScreen++;
          nodes.add(new Node(i, initialNodeX, nodesOnScreen-1, spaceBetween, lineHeight, display.getValue()));
        } else if (display.getValue() == 1 && nonFrequency[i] != 0) {
          nodesOnScreen++;
          nodes.add(new Node(i, initialNodeX, nodesOnScreen-1, spaceBetween, lineHeight, display.getValue()));
        } else if (display.getValue() == 2) {
          nodesOnScreen++;
          nodes.add(new Node(i, initialNodeX, nodesOnScreen-1, spaceBetween, lineHeight, display.getValue()));
        }
      }
    }
  }
}

