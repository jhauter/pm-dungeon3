package de.fhbielefeld.pmdungeon.quibble.hud;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.TextStage;

public class QuestHUD extends HUDElement
{
	private final TextStage textHUD;
	
	private final List<Label> labels;
	
	private List<Quest> quests;
	
	public QuestHUD(int x, int y, TextStage textHUD)
	{
		super(x, y);
		this.textHUD = textHUD;
		this.labels = new ArrayList<>();
	}
	
	public void refreshQuests()
	{
		this.labels.forEach(l -> l.setText(""));
		this.labels.clear(); //workaround for the bug
		
		Quest q;
		for(int i = 0; i < this.quests.size(); ++i)
		{
			q = this.quests.get(i);
			this.labels.add(this.textHUD.drawText(q.getTask(), DungeonStart.FONT_ARIAL, Color.WHITE, 16, 256, 32, this.getX(), this.getY() - i * 96 + 48));
			this.labels.add(this.textHUD.drawText(q.getProgress(), DungeonStart.FONT_ARIAL, Color.YELLOW, 16, 256, 32, this.getX(), this.getY() - i * 96 + 24));
			this.labels.add(this.textHUD.drawText(q.getReward(), DungeonStart.FONT_ARIAL, Color.GREEN, 16, 256, 32, this.getX(), this.getY() - i * 96));
		}
	}
	
	public void setQuests(List<Quest> quests)
	{
		this.quests = quests;
	}

	@Override
	public String getTexturePath()
	{
		return null;
	}
	
	@Override
	public void render(SpriteBatch batch, int renderX, int renderY)
	{
		
	}
}
