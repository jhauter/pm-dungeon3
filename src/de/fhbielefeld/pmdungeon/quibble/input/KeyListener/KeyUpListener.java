package de.fhbielefeld.pmdungeon.quibble.input.keyListener;

import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.quibble.input.InputListener;
import de.fhbielefeld.pmdungeon.quibble.input.KEY;
import de.fhbielefeld.pmdungeon.quibble.input.KeyListener;

public class KeyUpListener extends KeyListener implements InputListener{

  private Knight knight;
  
  public KeyUpListener(Knight knight) {
    super(knight);
    this.knight = knight;
  }
  
  @Override
  public void onInputRecieved(KEY eventName) {
    if(eventName == KEY.UP)
      this.knight.setPosition(knight.getPosition().x, knight.getPosition().y + knight.getInitWalkingSpeed());
  }
  
  
}
