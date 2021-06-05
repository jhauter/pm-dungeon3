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
