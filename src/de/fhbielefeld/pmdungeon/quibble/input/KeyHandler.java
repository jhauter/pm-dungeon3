package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyHandler implements InputHandler {

  private ArrayList<InputListener> listener = new ArrayList<>();
  
  @Override
  public void addInputListener(InputListener listener) {
    this.listener.add(listener);
  }

  @Override
  public void removeInputListener(InputListener listener) {
    this.listener.remove(listener);
  }

  @Override
  public void notityListeners(KEY eventName) {
    if(eventName != null)
    for (InputListener inputListener : listener) {
      inputListener.onInputRecieved(eventName);
    }
  }

  @Override
  public KEY updateHandler() {
    if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A))
      return KEY.UP_LEFT;
    if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A))
      return KEY.DOWN_LEFT;
    if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D))
      return KEY.DOWN_RIGHT;
    if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D))
      return KEY.UP_RIGHT;
    if (Gdx.input.isKeyPressed(Input.Keys.W))
      return KEY.UP;
    if (Gdx.input.isKeyPressed(Input.Keys.D))
      return KEY.RIGHT;
    if (Gdx.input.isKeyPressed(Input.Keys.S))
      return KEY.DOWN;
    if (Gdx.input.isKeyPressed(Input.Keys.A))
      return KEY.LEFT;
    return KEY.NO_KEY;
  }

  @Override
  public void registerKeyEvent(KEY key) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void registerMouseEvent(KEY key) {
    // TODO Auto-generated method stub
    
  }

}
