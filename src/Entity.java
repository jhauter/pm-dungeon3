import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;

import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class Entity implements IEntity, IAnimatable
{
	private long ticks;
	
	private Point position;
	
	private Animation idleAnimation;
	
	public Entity(float x, float y)
	{
		this.position = new Point(x, y);
		this.loadFileData();
	}
	
	public Entity()
	{
		this(0.0F, 0.0F);
	}
	
	private void loadFileData()
	{
		List<Texture> idleTex = new ArrayList<Texture>();
		for(int i = 0; i < 4; ++i)
		{
			idleTex.add(new Texture("assets/textures/obj/big_demon_idle_anim_f" + i + ".png"));
		}
		this.idleAnimation = new Animation(idleTex, 3);
	}
	
	@Override
	public Point getPosition()
	{
		return this.position;
	}
	
	@Override
	public Animation getActiveAnimation()
	{
		return this.idleAnimation;
	}
	
	@Override
	public boolean deleteable()
	{
		return false;
	}
	
	@Override
	public void update()
	{
//		System.out.println(this.ticks);
		this.ticks++;
		this.draw(0.0F, (float)Math.sin(ticks / 1.0F) * 1.2F);
		
		
	}
	
	public void move(float x, float y)
	{
		this.position.x += x;
		this.position.y += y;
		//collision detection
	}
}
