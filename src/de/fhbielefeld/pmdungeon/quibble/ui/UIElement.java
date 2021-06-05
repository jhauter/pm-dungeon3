package de.fhbielefeld.pmdungeon.quibble.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public abstract class UIElement
{
	protected float x, y, width = 32, height = 32;
	
	UILayer uiLayer;
	
	public UIElement()
	{
		
	}
	
	public abstract void draw(Batch batch);
	
	public boolean onClick(InputEvent event, float x, float y, int pointer, int button)
	{
		return false;
	}
	
	public boolean onClickWithGrabbedElement(InputEvent event, float x, float y, int pointer, int button, UIElement grabbedElement)
	{
		return false;
	}
	
	public boolean onClickAsGrabbedElement(InputEvent event, float x, float y, int pointer, int button, UIElement clickedElement)
	{
		return false;
	}
	
	public final void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public final void setSize(float width, float height)
	{
		this.width = width;
		this.height = height;
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
	public float getWidth()
	{
		return this.width;
	}
	
	public float getHeight()
	{
		return this.height;
	}
	
	public final UILayer getUILayer()
	{
		return this.uiLayer;
	}
	
	public void onRemove()
	{
		
	}
}
