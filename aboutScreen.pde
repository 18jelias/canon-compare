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
  
  void draw() {
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
  
  void drawHeader() {
    fill(73,242,181);
    rectMode(CORNER);
    rect(0,0,width,100);
    image(logo, 0, 0, 150, 100); 
    textFont(font2, 48);
    fill(0);
    text("Canon Compare", 200, 75);
  }
  
  void scroll() {
    if (show == SHOW_DESCRIP) {
      
    } else if (show == SHOW_METHOD) {
      
    } else if (show == SHOW_CONTACT) {
      
    }
  }
  
  void whenPressed() {
    if (b_enter.isClicked()) {
      if(navigation.getValue() == 0.0) state = STATE_HOME;
      else if(navigation.getValue() == 1.0) show =  SHOW_METHOD;
      else if(navigation.getValue() == 2.0) show = SHOW_CONTACT;
      else if(navigation.getValue() == 3.0) show = SHOW_DESCRIP;
    }
    //else if (b_contact.isClicked()) show = SHOW_CONTACT;
    //else if (b_home.isClicked()) state = STATE_HOME;
    //else if (b_description.isClicked()) show = SHOW_DESCRIP;
  }
}
