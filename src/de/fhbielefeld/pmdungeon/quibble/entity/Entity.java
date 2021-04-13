package de.fhbielefeld.pmdungeon.quibble.entity;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandler;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IAnimatable;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public abstract class Entity implements IEntity, IAnimatable
{
	private long ticks;
	
	private Point position;
	
	protected AnimationHandler animationHandler;
	
	protected DungeonWorld world;
	
	public Entity(float x, float y)
	{
		this.position = new Point(x, y);
	}
	
	public Entity()
	{
		this(0.0F, 0.0F);
	}
	
	public void loadResources()
	{
//		List<Texture> idleTex = new ArrayList<Texture>();
//		for(int i = 0; i < 4; ++i)
//		{
//			idleTex.add(new Texture("assets/textures/entity/right/knight/knight_f_idle_anim_f" + i + ".png"));
//		}
//		this.idleAnimation = new Animation(idleTex, 3);
	}
	
	@Override
	public final Point getPosition()
	{
		return this.position;
	}
	
	public final void setPosition(float x, float y)
	{
		this.position.x = x;
		this.position.y = y;
	}
	
	public final long getTicks()
	{
		return this.ticks;
	}
	
	@Override
	public Animation getActiveAnimation()
	{
		return this.animationHandler.getCurrentAnimation();
	}
	
	@Override
	public boolean deleteable()
	{
		return false;
	}
	
	@Override
	public void update()
	{
		this.ticks++;
		this.draw(0.0F, (float)Math.sin(this.ticks / 10.0F) * 0.2F);
	}
	
	public void move(float x, float y)
	{
		this.position.x += x;
		this.position.y += y;
		//collision detection
	}
	
	public void onSpawn(DungeonWorld world)
	{
		this.world = world;
	}
}
