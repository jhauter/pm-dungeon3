package de.fhbielefeld.pmdungeon.quibble.input;

public enum KEY {
  
  
  UP("Up", 2, false),
  UP_RIGHT("UpRight", 2, false),
  RIGHT("Right", 2, false),
  DOWN_RIGHT("DownRight", 2, false),
  DOWN("Down", 2, false),
  DOWN_LEFT("DownLeft", 2, false),
  LEFT("Left", 2, false),
  UP_LEFT("UpLeft", 2, false),
  NO_KEY("noKey", 3, false);
  
  String keyDirection;
  int priority;
  boolean justPressed;
  
  KEY(String title, int prio, boolean justPressed) {
  }
  
  

}
