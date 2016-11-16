import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Timeline extends PApplet {



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

public void setup() {
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


public void draw() {
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

public void clearData() {
  dataCount = loadTable("DataCount.csv", "header");
  for (int i = 0; i < dataCount.getRowCount(); i++) {
    dataCount.setInt(i, "Canonical", 0);
    dataCount.setInt(i, "Noncanonical", 0);
    dataCount.setInt(i, "Frequency", 0);
  }

  saveTable(dataCount, "data/Datacount.csv", "csv");
}


public void keyPressed() {
  if(state == STATE_ARCGRAPH) aGraph.scroll();
  if(state == STATE_POPUP_TEXT) compareText.whenKeyPressed();
  if(state == STATE_ABOUT) about.scroll();
 }
 

 
 public void mousePressed() {
   if (state == STATE_ARCGRAPH) {
     aGraph.whenPressed();
   } else if (state == STATE_HOME) {
     home.whenPressed();
   } else if (state == STATE_ABOUT) {
     about.whenPressed();
   }
 }
 




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

  public void draw() {
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

  public void drawLines() {
    for (int i = 1; i <= max (frequency); i++) {
      strokeWeight(2);
      stroke(255);
      fill(255);
      line(0, -i*lineHeight, width, -i*lineHeight);
      textFont(font, 12);
      text(i, 5, -i*lineHeight-3);
    }
  }

  public void drawKey() {
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

  public void scroll() {
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

  public void whenPressed() {
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

  public void drawHeader() {
    pushStyle();
    fill(255, 255, 255);
    ellipse(width/2, 35, width, 70);
    fill(112, 206, 203);
    textFont(title, 48);
    text("Frequency of Events", width/2 - textWidth("Frequency of Events")/2, 50);
    home.draw();
    popStyle();
  }

  public void updateNodes() {
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

class Button {
  
  int x;
  int y;
  int l;
  int w;
  int c;
  String s;
  int tSize;
  PFont bFont;
  
  Button() {
    this(width / 2, height / 2, "Enter");   
  }
  
  Button(int x, int y, String s) {
    this(x, y, color(100, 100, 100, 100), s);
  }
  
  Button(int x, int y, int l, int w, int c, String s) {
    this(x, y, l, w, c, s, 32, loadFont("AppleGothic-24.vlw"));
  }
  
  Button(int x, int y, int c, String s) {
    this(x, y, 160, 65, c, s, 32, loadFont("AppleGothic-24.vlw"));
  }
  
  Button(int x, int y, int l, int w, int c, String s, int tSize) {
    this(x, y, l, w, c, s, tSize, loadFont("AppleGothic-24.vlw"));
  }
  
  Button(int x, int y, int l, int w, int c, String s, int tSize, PFont bFont) {
    this.x = x; 
    this.y = y;
    this.l = l;
    this.w = w;
    this.c = c;
    this.s = s;
    this.tSize = tSize;
    this.bFont = bFont;
    
  }
 
  
  public boolean isClicked() {
    println(x, y);
    if ((mouseX >= x - l / 2 && mouseX <= (x + l / 2)) && 
        (mouseY >= y - w / 2 && mouseY <= (y + w / 2))) {
       return true;  
        
    } else {
      return false;
    }   
  }
  
  public void draw() {
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
class Node {
  int w;
  int h; 
  int x;
  int y;
  int index; 
  int onScreenIndex;
  int spaceBetween;
  int heightScale;
  int c; 
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

  public void draw() {
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

  public void whenPressed() {
    compareText = new popupText(index);
    state = STATE_POPUP_TEXT;
  }
}

class aboutScreen {
  
  int show;
  final int SHOW_METHOD = 0;
  final int SHOW_CONTACT = 1;
  final int SHOW_DESCRIP = 2;
  
  Button b_enter;
  DropdownList navigation;
  
  int x;
  int y;
  
  String[] s_method;
  String[] s_contact;
  String[] s_description;
  
  PImage logo; 
  PFont font;
  PFont font2;
  
  
  aboutScreen() {
    show = SHOW_DESCRIP;
    b_enter = new Button(width-100, 50, 125, 75, color(0), "Enter");
    navigation = new DropdownList(cp5, "Go to");
    String[] listNames = {"Home", "Method", "Contact", "Description"};
    font = loadFont("AppleGothic-24.vlw");
    font2 = loadFont("CopperplateGothic-Bold-48.vlw");
    cp5.setFont(font, 32);
    navigation.setPosition(width-500, 75)
              .addItems(listNames)
              .setItemHeight(50)
              .setBarHeight(50)
              .setWidth(300)
              .setColorBackground(color(100,100,100,150))
              .setColorForeground(color(255, 255, 255))
              .setColorActive(color(156, 198, 192))
              .setHeight(400)
              .setScrollbarWidth(50)
              ;
    s_method = loadStrings("methodText.txt");
    s_description = loadStrings("descriptionText.txt");
    logo = loadImage("logoresize.png");
    x = 20;

    y = 120;
  }
  
  public void draw() {
    background(255);
    drawHeader();
    textFont(font, 24);
    b_enter.draw();
    if (show == SHOW_DESCRIP) {
      for (int i = 0; i < s_description.length; i++) {
        text(s_description[i], x, y+i*32, width - 40, textWidth(s_description[i]));
      }
    } else if (show == SHOW_METHOD) {
      for (int i = 0; i < s_method.length; i++) {
        text(s_method[i], x, y+i*32, width - 40, textWidth(s_method[i]));
      }
    } else if (show == SHOW_CONTACT) {
      String s = "If any questions may arise about this project, feel free to contact the developer of this project, Jacquelyn Elias.";
      String s2= "Jacquelyn Elias";
      String s3= "jelias@smu.edu";
      String s4= "SMU Meadows School of the Arts";
      text(s, width/2-textWidth(s)/2, y+50, width - 40, textWidth(s));
      text(s2, width/2-textWidth(s2)/2, y+120, width - 40, textWidth(s2));
      text(s3, width/2-textWidth(s3)/2, y+150, width - 40, textWidth(s3));
      text(s4, width/2-textWidth(s4)/2, y+180, width - 40, textWidth(s4));
    }
    println(show);
  }
  
  public void drawHeader() {
    fill(73,242,181);
    rectMode(CORNER);
    rect(0,0,width,100);
    image(logo, 0, 0, 150, 100); 
    textFont(font2, 48);
    fill(0);
    text("Canon Compare", 200, 75);
  }
  
  public void scroll() {
    if (show == SHOW_DESCRIP) {
      
    } else if (show == SHOW_METHOD) {
      
    } else if (show == SHOW_CONTACT) {
      
    }
  }
  
  public void whenPressed() {
    if (b_enter.isClicked()) {
      if(navigation.getValue() == 0.0f) state = STATE_HOME;
      else if(navigation.getValue() == 1.0f) show =  SHOW_METHOD;
      else if(navigation.getValue() == 2.0f) show = SHOW_CONTACT;
      else if(navigation.getValue() == 3.0f) show = SHOW_DESCRIP;
    }
    //else if (b_contact.isClicked()) show = SHOW_CONTACT;
    //else if (b_home.isClicked()) state = STATE_HOME;
    //else if (b_description.isClicked()) show = SHOW_DESCRIP;
  }
}
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
  
  public void draw() {  
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
  
  public void whenPressed() {
    if (b_arcGraph.isClicked()) state = STATE_ARCGRAPH;
    else if (b_about.isClicked()) state = STATE_ABOUT;
  }
}

int[] nonFrequency;
int[] canFrequency;
int[] graphC; 
Table dataCount;


public void sortChronological(Table t, String s) {
  t.sort(s);
}

public void loadData(Table t) {
  events = new String[numOfEvents];
  frequency = new int[numOfEvents]; 
  nonFrequency = new int[numOfEvents];
  canFrequency = new int[numOfEvents];
  graphC = new int[numOfEvents]; 
}


String[] canFiles = { "John.txt", "Luke.txt", "Mark.txt", "Matthew.txt" };
String[] canFilesNames = { "John", "Luke", "Mark", "Matthew" };
String[] nonCanFiles = { "James.html", "Marcion.html", "MaryMagdalene.html", 
                         "NativityofMary.html", "Nicodemus.html", "Peter.html",
                         "Phillip.html", "PseudoMatthew.html", "Thomas.html", 
                         "ThomasCompilation.html", "ThomasGreekA.html",
                         "ThomasGreekB.html" };
String[] nonCanFilesNames = { "James", "Marcion", "Mary Magdalene", 
                         "Nativity of Mary", "Nicodemus", "Peter",
                         "Phillip", "Pseudo Matthew", "Thomas", 
                         "Thomas Compilation", "Thomas Greek A",
                         "Thomas Greek B" };                  
                         

ArrayList<Text> texts = new ArrayList<Text>();  

public void importText() {
  Table t;
  t = loadTable("DataCount.csv", "header");
  numOfEvents = t.getRowCount();
  tags = new String[numOfEvents];
  for (int i = 0; i < numOfEvents; i++) {
    tags[i] = t.getString(i, "Tag");
  }
  
  for (int i = 0; i < canFiles.length; i++) {
    texts.add(new Text(canFiles[i], true, canFilesNames[i]));
  }
  
  for (int i = 0; i < nonCanFiles.length; i++) {
    texts.add(new Text(nonCanFiles[i], false, nonCanFilesNames[i]));
  }
  for (int i = 0; i < texts.size(); i++) {
    texts.get(i).search();
    for (int j = 0; j < numOfEvents; j++) {
      int input = t.getInt(j, "Frequency") + texts.get(i).frequency[j]; 
      t.setInt(j, "Frequency", input);
      if (texts.get(i).canon) {
        int input2 = t.getInt(j, "Canonical") + texts.get(i).frequency[j]; 
        t.setInt(j, "Canonical", input2); 
      } else if (texts.get(i).canon ==false) {
        int input2 = t.getInt(j, "Noncanonical") + texts.get(i).frequency[j]; 
        t.setInt(j, "Noncanonical", input2); 
      }
    }
  }
  saveTable(t, "data/Datacount.csv", "csv");
}


class popupText {
  int w;
  int h;
  int textX; 
  int textY;
  ArrayList<String> s;
  ArrayList<String> name; 
  int index;
  int c;

  popupText(int index) {
    this.index = index;
    s = new ArrayList<String>();
    name = new ArrayList<String>();
    updateText();
    textY = 55;
  }

  public void draw() {
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

  public void whenKeyPressed() {
    if (keyCode == ENTER) {
      state = STATE_ARCGRAPH;
      textY = 55;
    } else if (keyCode == UP) {
      textY -=5;
    } else if (keyCode == DOWN) {
      if (textY <= 55) textY += 5;
      
    }
  }

  public void updateText() {
    for (Text t : texts) {
      if (t.frequency[index] > 0) {
        s.add(t.retrieveText(index));
        name.add(t.name);
      }
    }
  }
}

class Text {
  String name;
  int[] frequency;
  String[] copy;
  String link; 
  boolean canon;

  Text() {
  }

  Text(String link, boolean canon, String name) {
    copy = loadStrings(link);
    this.canon = canon; 
    this.name = name;
    frequency = new int[numOfEvents];
    for (int i = 0; i < frequency.length; i++) frequency[i]=0;
  }

  public void search() {
    for (int i = 0; i < numOfEvents; i++) { 
      for (int k = 0; k < copy.length; k++) {
        if (copy[k].contains("<" + tags[i] + ">")) this.frequency[i]++;
      }
    }
  }

  public String retrieveText(int tagIndex) {
    String returnString = "0";
    for (int k = 0; k < copy.length; k++) {
      if (copy[k].contains("<" + tags[tagIndex] + ">")) {
        int startIndex = copy[k].indexOf("<" + tags[tagIndex] + ">");
        int endIndex = copy[k].indexOf("</" + tags[tagIndex] + ">");
        if (endIndex > startIndex) {
          returnString = copy[k].substring(startIndex, endIndex);
        } else if (startIndex > endIndex) {
          returnString = copy[k].substring(endIndex, startIndex);
        }
      }
    }
    returnString.replaceAll("<" + tags[tagIndex] + ">", "");
    returnString.replaceAll("</" + tags[tagIndex] + ">", "");
    returnString.replaceAll("<p>", "");
    returnString.replaceAll("</p>", "");
    return returnString;
  }
  
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "Timeline" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
