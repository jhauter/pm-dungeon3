package de.fhbielefeld.pmdungeon.quibble.input.keyListener;

import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.quibble.input.KEY;
import de.fhbielefeld.pmdungeon.quibble.input.KeyListener;

public class KeyRightListener extends KeyListener{

  private Knight knight;
  
  public KeyRightListener(Knight knight) {
    super(knight);
    this.knight = knight;
  }

  @Override
  public void onInputRecieved(KEY eventName) {
    if(eventName == KEY.RIGHT)
      this.knight.setPosition(knight.getPosition().x + knight.getInitWalkingSpeed(), knight.getPosition().y);
    
  }

}
