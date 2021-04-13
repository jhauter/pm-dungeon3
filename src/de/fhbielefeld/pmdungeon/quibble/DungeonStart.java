package de.fhbielefeld.pmdungeon.quibble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;

public class DungeonStart extends MainController
{
	public static void main(String[] args)
	{
		DesktopLauncher.run(new DungeonStart());
	}
	
	/****************************************
	 *                GAME                  *
	 ****************************************/
	
	private Entity myHero;
	
	public DungeonStart()
	{
		
	}
	
	@Override
	protected void setup()
	{
		super.setup();
	}
	
	@Override
	public void onLevelLoad()
	{
		super.onLevelLoad();
		this.myHero = new Knight();
		this.entityController.addEntity(this.myHero);
		this.camera.follow(this.myHero);
		Gdx.app.log("GAME", "Level loaded.");
	}
	
	@Override
	protected void beginFrame()
	{
		super.beginFrame();
		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			this.myHero.move(-0.1F, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			this.myHero.move(+0.1F, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			this.myHero.move(0, +0.1F);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			this.myHero.move(0, -0.1F);
		}
	}
	
	@Override
	protected void endFrame()
	{
		super.endFrame();
	}
}
