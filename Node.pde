class Node {
  int w;
  int h; 
  int x;
  int y;
  int index; 
  int onScreenIndex;
  int spaceBetween;
  int heightScale;
  color c; 
  float numOfArc;

    Node(int index, int initialNodeX, int onScreenIndex, int spaceBetween, int heightScale, float numOfArc) {
    this.index = index; 
    this.onScreenIndex = onScreenIndex;
    this.heightScale = heightScale;
    c = graphC[index];
    w = 180; 
    h = 2*frequency[index]*heightScale;
    this.spaceBetween = spaceBetween;
    x = initialNodeX + (spaceBetween+w)*onScreenIndex;
    y = 0;
    this.numOfArc = numOfArc;
  }

  void draw() {
    if (numOfArc == 1) {
      stroke(255);
        fill(112, 206, 168);
        arc(x, y, w, 2*nonFrequency[index]*heightScale, -PI, 0);
            stroke(112, 206, 204);
    textFont(font, 18);
    fill(graphC[index]);
    text(events[index], x-(textWidth(events[index])/2), 30);
      
    } else if (numOfArc == 0) {
      stroke(255);
        fill(225, 162, 240);
        arc(x, y, w, 2*canFrequency[index]*heightScale, -PI, 0);
            stroke(112, 206, 204);
    textFont(font, 18);
    fill(graphC[index]);
    text(events[index], x-(textWidth(events[index])/2), 30);
    } else {
      stroke(255);
      fill(c);
      arc(x, y, w, h, -PI, 0);
      if (nonFrequency[index] > canFrequency[index]) {
        fill(112, 206, 168);
        arc(x, y, w, 2*nonFrequency[index]*heightScale, -PI, 0);
        fill(225, 162, 240);
        arc(x, y, w, 2*canFrequency[index]*heightScale, -PI, 0);
      } else if (nonFrequency[index] < canFrequency[index]) {
        fill(225, 162, 240);
        arc(x, y, w, 2*canFrequency[index]*heightScale, -PI, 0); 
        fill(112, 206, 168);
        arc(x, y, w, 2*nonFrequency[index]*heightScale, -PI, 0);
      } else if (nonFrequency[index] == canFrequency[index]) {
        fill(88, 184, 242);
        arc(x, y, w, 2*nonFrequency[index]*heightScale, -PI, 0);
      }
     stroke(112, 206, 204);
    textFont(font, 18);
    fill(graphC[index]);
    text(events[index], x-(textWidth(events[index])/2), 30);
    }

  }

  void whenPressed() {
    compareText = new popupText(index);
    state = STATE_POPUP_TEXT;
  }
}

