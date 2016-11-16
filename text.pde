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

  void search() {
    for (int i = 0; i < numOfEvents; i++) { 
      for (int k = 0; k < copy.length; k++) {
        if (copy[k].contains("<" + tags[i] + ">")) this.frequency[i]++;
      }
    }
  }

  String retrieveText(int tagIndex) {
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

