import controlP5.*;

ControlP5 cp5;

final int STATE_ARCGRAPH = 0;
final int STATE_POPUP_TEXT = 1;
final int STATE_HOME = 2;
final int STATE_ABOUT = 3;

int state;
PFont font;
PFont title;
String[] events;
int numOfEvents;
int[] frequency;
String[] tags;
ArcGraph aGraph;
homeScreen home;
aboutScreen about;
popupText compareText;

void setup() {
  background(0,0,0);
  size(displayWidth,displayHeight-100);
  cp5 = new ControlP5(this);
  state = STATE_HOME;
  clearData();
  importText();
  dataCount = loadTable("DataCount.csv", "header");
  sortChronological(dataCount, "Order");
  loadData(dataCount);
  
  font = loadFont("AppleGothic-24.vlw");
  title = loadFont ("CopperplateGothic-Bold-48.vlw");
  
  for (int i = 0; i < numOfEvents; i++) {
    events[i] = dataCount.getString(i, "Event");
    frequency[i] = dataCount.getInt(i, "Frequency");
    nonFrequency[i] = dataCount.getInt(i, "Noncanonical");
    canFrequency[i] = dataCount.getInt(i, "Canonical");
    graphC[i] = color(random(100,255), random(100,255), random(100,255));
  }
  
    aGraph = new ArcGraph();
    home = new homeScreen();
    about = new aboutScreen();

  
}


void draw() {
  background(0,0,0);
  if (state == STATE_ARCGRAPH) {
    aGraph.draw();
    about.navigation.hide();
    aGraph.display.show();
  } else if(state == STATE_POPUP_TEXT) {
    aGraph.draw();
    compareText.draw();
    about.navigation.hide();
    aGraph.display.hide();
  } else if(state == STATE_HOME) {
    home.draw();
    about.navigation.hide();
    aGraph.display.hide();
  } else if (state== STATE_ABOUT) {
    about.draw();
    about.navigation.show();
    aGraph.display.hide();
  }
    
  
}

void clearData() {
  dataCount = loadTable("DataCount.csv", "header");
  for (int i = 0; i < dataCount.getRowCount(); i++) {
    dataCount.setInt(i, "Canonical", 0);
    dataCount.setInt(i, "Noncanonical", 0);
    dataCount.setInt(i, "Frequency", 0);
  }

  saveTable(dataCount, "data/Datacount.csv", "csv");
}


void keyPressed() {
  if(state == STATE_ARCGRAPH) aGraph.scroll();
  if(state == STATE_POPUP_TEXT) compareText.whenKeyPressed();
  if(state == STATE_ABOUT) about.scroll();
 }
 

 
 void mousePressed() {
   if (state == STATE_ARCGRAPH) {
     aGraph.whenPressed();
   } else if (state == STATE_HOME) {
     home.whenPressed();
   } else if (state == STATE_ABOUT) {
     about.whenPressed();
   }
 }
 




