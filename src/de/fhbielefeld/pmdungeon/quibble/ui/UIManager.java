package de.fhbielefeld.pmdungeon.quibble.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;

public class UIManager
{
	private List<UILayer> uiList;
	
	private UILayerZOrderComparator zOrderComparator;
	
	private UIElement elementOnMouse;
	
	public UIManager()
	{
		this.uiList = new ArrayList<>();
		this.zOrderComparator = new UILayerZOrderComparator();
	}
	
	public void draw()
	{
		for(int i = 0; i < this.uiList.size(); ++i)
		{
			this.uiList.get(i).draw();
		}
		
		if(this.elementOnMouse != null)
		{
			DungeonStart.getGameBatch().setProjectionMatrix(DungeonStart.orthoProjMatrix);
			
			this.elementOnMouse.x = Gdx.input.getX() - this.elementOnMouse.width / 2;
			this.elementOnMouse.y = Gdx.graphics.getHeight() - Gdx.input.getY() - this.elementOnMouse.height / 2;
			
			this.elementOnMouse.draw(DungeonStart.getGameBatch());
			DungeonStart.getGameBatch().setProjectionMatrix(DungeonStart.getDungeonMain().getCamera().combined);
		}
	}
	
	/**
	 * Adds an <code>UILayer</code> to the UI.
	 * UILayers are layers on the screen that contain UI elements, like text...
	 * For example, the health and level HUD is on a UILayer.
	 * Each inventory that is open is on a different UILayer.
	 * Override <code>UILayer</code>, add stuff to it and add it here afterwards to show it on the screen!
	 * Take a look at existing UILayers for more examples.
	 * @param ui the UILayer to add
	 */
	public void addUI(UILayer ui)
	{
		if(this.uiList.contains(ui))
		{
			throw new IllegalArgumentException("cannot add the same UI twice");
		}
		if(ui.uiManager != null)
		{
			throw new IllegalArgumentException("this UILayer is already added to another UI manager");
		}
		this.uiList.add(ui);
		ui.uiManager = this;
		this.zOrderChanged();
	}
	
	public void removeUI(UILayer ui)
	{
		this.uiList.remove(ui);
		ui.uiManager = null;
		this.zOrderChanged();
	}
	
	public InputProcessor[] getInputProcessors()
	{
		InputProcessor[] p = new InputProcessor[this.uiList.size()];
		for(int i = 0, n = p.length; i < n; ++i)
		{
			p[i] = this.uiList.get(n - i - 1).getStage();
		}
		return p;
	}
	
	public void zOrderChanged()
	{
		this.uiList.sort(this.zOrderComparator);
	}
	
	public void setElementOnMouse(UIElement element)
	{
		if(this.elementOnMouse != null && this.elementOnMouse != element)
		{
			this.elementOnMouse.onRemove();
		}
		this.elementOnMouse = element;
	}
	
	public UIElement getElementOnMouse()
	{
		return this.elementOnMouse;
	}
}
