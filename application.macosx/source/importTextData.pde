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

void importText() {
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


