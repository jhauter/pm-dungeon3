import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;

public class TestMain extends MainController
{
	public static void main(String[] args)
	{
		DesktopLauncher.run(new TestMain());
	}
	
	/****************************************
	 *                GAME                  *
	 ****************************************/
	
	private Entity myHero;
	
	public TestMain()
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
		this.myHero = new Entity();
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
		// TODO Auto-generated method stub
		super.endFrame();
	}
}
