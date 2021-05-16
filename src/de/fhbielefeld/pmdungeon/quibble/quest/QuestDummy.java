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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean deleteable() {
		return decide;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateLogic() {
		super.updateLogic();

	}

	/**
	 * {@inheritDoc}
	 */
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
				Quest newQuest = null;
				if (quest == QuestTypes.QUEST_LEVEL_UP) {
					int level = player.totalExpFunction(player.getCurrentExpLevel()) / 10;
					newQuest = new RQuestLevelUp(quest.questName, player, Item.POTION_RED_BIG, 20, level + 1);
				}
				else if (quest == QuestTypes.QUEST_NEXT_STAGE) {
					newQuest = new RQuestDungeonStage(quest.questName, player, Item.SWORD_KATANA, 20);
				}
				else if (quest == QuestTypes.QUEST_KILL) {
					newQuest = new RQuestKillMonster(quest.questName, player, null, 20, 5);
				}
				else
				{
					return;
				}
				player.addQuest(newQuest);
				setDecided();
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
	 * if set the quest becomes active and the doll disappears
	 */
	public void setDecided() {
		this.decide = true;
	}
}
