package de.fhbielefeld.pmdungeon.quibble.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.quest.Quest;

public class UILayerQuestView extends UILayer
{
	private static class QuestWrapper
	{
		private Label lTask;
		private Label lProgress;
		private Label lReward;
		private Quest quest;
		
		private QuestWrapper(Label lt, Label lp, Label lr, Quest q)
		{
			this.lTask = lt;
			this.lProgress = lp;
			this.lReward = lr;
			this.quest = q;
		}
	}
	
	private Player player;
	
	private List<QuestWrapper> questTexts;
	
	private float x;
	private float y;
	
	public UILayerQuestView(float x, float y)
	{
		this.questTexts = new ArrayList<>();
		this.x = x;
		this.y = y;
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	/**
	 * Needs to be called to notify the HUD that the quest list has been changed.
	 */
	public void refreshQuests()
	{
		if(this.player == null)
		{
			return;
		}
		
		QuestWrapper qw;
		for(int i = 0; i < this.questTexts.size(); ++i)
		{
			qw = this.questTexts.get(i);
			this.removeText(qw.lProgress);
			this.removeText(qw.lReward);
			this.removeText(qw.lTask);
		}
		this.questTexts.clear();
		
		Quest q;
		Label lt, lp, lr;
		for(int i = 0; i < this.player.getQuestList().size(); ++i)
		{
			q = this.player.getQuestList().get(i);
			lt = this.addText(q.getTask(), UIFonts.DEFAULT, this.x, this.y - i * 96 + 48);
			lp = this.addText(q.getProgressText(), UIFonts.DEFAULT, this.x, this.y - i * 96 + 24);
			lr = this.addText("Reward: " + q.getRewardText(), UIFonts.DEFAULT, this.x, this.y - i * 96);

			lt.setColor(Color.WHITE);
			lp.setColor(Color.YELLOW);
			lr.setColor(Color.GREEN);
			
			this.questTexts.add(new QuestWrapper(lt, lp, lr, q));
		}
	}
	
	@Override
	public void draw()
	{
		super.draw();
		
		QuestWrapper w;
		for(int i = 0; i < this.questTexts.size(); ++i)
		{
			w = this.questTexts.get(i);
			w.lTask.setText(w.quest.getTask());
			w.lProgress.setText(w.quest.getProgressText());
			w.lReward.setText("Reward: " + w.quest.getRewardText());
		}
	}
}
