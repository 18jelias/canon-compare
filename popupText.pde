class popupText {
  int w;
  int h;
  int textX; 
  int textY;
  ArrayList<String> s;
  ArrayList<String> name; 
  int index;
  color c;

  popupText(int index) {
    this.index = index;
    s = new ArrayList<String>();
    name = new ArrayList<String>();
    updateText();
    textY = 55;
  }

  void draw() {
    pushStyle();
    fill(255, 255, 255, 220);
    rectMode(CENTER);
    rect(width/2, height/2, width, height);
    fill(0);
    rectMode(CORNER);
    textSize(16);
    for (int i = 0; i < s.size(); i++) {
      text(s.get(i), 5+30*i+(i*(width-30*s.size())/s.size()), textY, (width-(30*s.size()))/(s.size()), textWidth(s.get(i)));
    }
    fill(255, 0, 0);
    if(s.size() > 5) textSize(18); 
    else textSize(32);
    fill(255, 255, 255, 255);
    rect(0, 0, width, 50);
    fill(graphC[index], 255);
    for (int i = 0; i < s.size(); i++) {
      text(name.get(i), 5+30*i+(i*(width-30*s.size())/s.size()), 40);
    }
    popStyle();
  }

  void whenKeyPressed() {
    if (keyCode == ENTER) {
      state = STATE_ARCGRAPH;
      textY = 55;
    } else if (keyCode == UP) {
      textY -=5;
    } else if (keyCode == DOWN) {
      if (textY <= 55) textY += 5;
      
    }
  }

  void updateText() {
    for (Text t : texts) {
      if (t.frequency[index] > 0) {
        s.add(t.retrieveText(index));
        name.add(t.name);
      }
    }
  }
}

