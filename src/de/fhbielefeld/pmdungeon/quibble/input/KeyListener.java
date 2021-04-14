package de.fhbielefeld.pmdungeon.quibble.input;

import de.fhbielefeld.pmdungeon.quibble.entity.Knight;

public abstract class KeyListener implements InputListener {
  
  protected Knight knight;
  
  public KeyListener(Knight knight) {
    this.knight = knight;
  }

  @Override
  public abstract void onInputRecieved(KEY eventName);

  
  
}
