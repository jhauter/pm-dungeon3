package de.fhbielefeld.pmdungeon.quibble.hud;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.quest.Quest;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.TextStage;

public class QuestHUD extends HUDElement
{
	private static class LabelWrapper
	{
		private Label lTask;
		private Label lProgress;
		private Label lReward;
		private Quest quest;
		
		private LabelWrapper(Label lt, Label lp, Label lr, Quest q)
		{
			this.lTask = lt;
			this.lProgress = lp;
			this.lReward = lr;
			this.quest = q;
		}
		
		private void setEmptyText()
		{
			this.lTask.setText("");
			this.lProgress.setText("");
			this.lReward.setText("");
		}
	}
	
	private final TextStage textHUD;
	
	private final List<LabelWrapper> labels;
	
	private List<Quest> quests;
	
	public QuestHUD(int x, int y, TextStage textHUD)
	{
		super(x, y);
		this.textHUD = textHUD;
		this.labels = new ArrayList<>();
	}
	
	public void refreshQuests()
	{
		this.labels.forEach(l -> l.setEmptyText());
		this.labels.clear(); //workaround for the bug
		
		Quest q;
		Label lt, lp, lr;
		for(int i = 0; i < this.quests.size(); ++i)
		{
			q = this.quests.get(i);
			lt = this.textHUD.drawText(q.getTask(), DungeonStart.FONT_ARIAL, Color.WHITE, 16, 256, 32, this.getX(), this.getY() - i * 96 + 48);
			lp = this.textHUD.drawText(q.onWork(), DungeonStart.FONT_ARIAL, Color.YELLOW, 16, 256, 32, this.getX(), this.getY() - i * 96 + 24);
			lr = this.textHUD.drawText(q.onComplete(), DungeonStart.FONT_ARIAL, Color.GREEN, 16, 256, 32, this.getX(), this.getY() - i * 96);
			this.labels.add(new LabelWrapper(lt, lp, lr, q));
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
		LabelWrapper w;
		for(int i = 0; i < this.labels.size(); ++i)
		{
			w = this.labels.get(i);
			w.lTask.setText(w.quest.getTask());
			w.lProgress.setText(w.quest.onWork());
			w.lReward.setText(w.quest.onComplete());
		}
	}
}
