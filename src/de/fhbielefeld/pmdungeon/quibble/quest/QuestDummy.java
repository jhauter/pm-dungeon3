package de.fhbielefeld.pmdungeon.quibble.quest;

import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class QuestDummy extends Entity {

	private boolean decide;
	private boolean isActive;

	private final QuestTypes quest;

	/**
	 * Will create a visible Quest Entity
	 * 
	 * @param quest the quest which should be visible
	 * @param x     x float of the Position
	 * @param y     y float of the Position
	 */
	public QuestDummy(QuestTypes quest, float x, float y) {
		super(x, y);
		this.quest = quest;
		this.animationHandler.addAsDefaultAnimation("default", 1, 1,
				Quest.QUEST_TEXTURE_PATH + quest.texture + ".png", -1);
	}

	@Override
	public boolean deleteable() {
		return decide;
	}

	@Override
	protected void updateLogic() {
		super.updateLogic();

	}

	@Override
	protected void onEntityCollision(Entity otherEntity) {
		super.onEntityCollision(otherEntity);
		if (!(otherEntity instanceof Player))
			return;
		if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			LoggingHandler.logger.log(Level.INFO, "\nYou have activated a Quest: " + quest.questName
					+ "\nWould you like to accept press J, else press N");
			setActive();
		}
		if (isActive) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
				Player player = (Player) otherEntity;
				if (quest.equals(QuestTypes.QUEST_YELLOW_FLAG)) {
					int level = player.totalExpFunction(player.getCurrentExpLevel()) / 10;
					Quest newQuest = new RQuestLevelUp("LevelQuest", player, level + 1, 20);
					player.addQuest(newQuest);
					setDecided();
				}
				if (quest.equals(QuestTypes.QUEST_BLUE_FLAG)) {
					Quest newQuest = new RQuestDungeonLevel("DungeonLevelQuest", player, Item.SWORD_KATANA);
					player.addQuest(newQuest);
					setDecided();
				}
				if (quest.equals(QuestTypes.QUEST_RED_FLAG)) {
					Quest newQuest = new RQuestKillMonster("Kill Quest", player, 5, 20);
					player.addQuest(newQuest);
					System.out.println("HHH");
					setDecided();
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
				setDecided();
				LoggingHandler.logger.log(Level.INFO, "Quest was declined");
			}
		}
	}

	/**
	 * Sets whether the quest can be accepted or rejected
	 * 
	 * @param isActive if true the quest can be decline or accept
	 */
	public void setActive() {
		this.isActive = true;
	}
	
	/**
	 * 
	 * @return the actual real quest of the doll
	 */


	/**
	 * if set the quest becomes active and the doll disappears
	 */
	public void setDecided() {
		this.decide = true;
	}
}
