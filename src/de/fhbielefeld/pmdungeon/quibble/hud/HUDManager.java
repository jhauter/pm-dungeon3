package de.fhbielefeld.pmdungeon.quibble.hud;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUDManager
{
	private final List<HUDElement> hudElements;
	
	private final List<HUDElement> deletable;
	
	private HUDElement elementOnMouse;
	
	private SpriteBatch batch;
	
	public HUDManager()
	{
		this.hudElements = new ArrayList<>();
		this.deletable = new ArrayList<>();
		this.batch = new SpriteBatch();
	}
	
	public HUDElement getElementOnMouse()
	{
		return this.elementOnMouse;
	}
	
	public void setElementOnMouse(HUDElement e)
	{
		if(this.elementOnMouse != null && e == null)
		{
			this.elementOnMouse.onDelete();
		}
		this.elementOnMouse = e;
	}
	
	public void addElement(HUDElement e)
	{
		this.hudElements.add(e);
	}
	
	public void addGroup(HUDGroup g)
	{
		this.hudElements.addAll(g.hudElements);
	}
	
	public void removeElement(HUDElement e)
	{
		this.hudElements.remove(e);
	}
	
	public void removeGroup(HUDGroup g)
	{
		this.hudElements.removeAll(g.hudElements);
	}
	
	public void update()
	{
		final int mx = Gdx.input.getX();
		final int my = Gdx.graphics.getHeight() - Gdx.input.getY();
		
		batch.begin();
		HUDElement e;
		for(int i = 0; i < this.hudElements.size(); ++i)
		{
			e = this.hudElements.get(i);
			if(e.shouldAutoDelete())
			{
				this.deletable.add(e);
				continue;
			}
			
			e.update();
			
			if(mx >= e.getX() && mx <= e.getX() + e.getWidth() &&
				my >= e.getY() && my <= e.getY() + e.getHeight() &&
				Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
			{
				if(this.elementOnMouse != null)
				{
					e.onClickWithGrabbedElement(this, this.elementOnMouse);
					this.elementOnMouse.onClickAsGrabbedElement(this, e);
				}
				e.onMouseClicked(this);
			}
			
			e.render(batch, e.getX(), e.getY());
		}
		
		if(this.elementOnMouse != null)
		{
			this.elementOnMouse.render(batch, mx - this.elementOnMouse.getWidth() / 2, my - this.elementOnMouse.getHeight() / 2);
		}
		
		batch.end();
		batch.flush();
		
		for(HUDElement toDelete : this.deletable)
		{
			toDelete.onDelete();
			this.hudElements.remove(toDelete);
		}
		if(this.elementOnMouse != null && elementOnMouse.shouldAutoDelete())
		{
			this.elementOnMouse.onDelete();
			this.elementOnMouse = null;
		}
	}
}
