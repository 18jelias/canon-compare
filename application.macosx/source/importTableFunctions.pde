
int[] nonFrequency;
int[] canFrequency;
color[] graphC; 
Table dataCount;


void sortChronological(Table t, String s) {
  t.sort(s);
}

void loadData(Table t) {
  events = new String[numOfEvents];
  frequency = new int[numOfEvents]; 
  nonFrequency = new int[numOfEvents];
  canFrequency = new int[numOfEvents];
  graphC = new color[numOfEvents]; 
}


