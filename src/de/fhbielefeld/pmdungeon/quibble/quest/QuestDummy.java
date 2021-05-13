package de.fhbielefeld.pmdungeon.quibble.quest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerInteractDummyQuestEvent;

public class QuestDummy extends Entity {

	private boolean isAccept;
	private boolean isActive;

	private final Quest quest;
	private Player player;

	/**
	 * Will create a visible Quest Entity
	 * 
	 * @param quest the quest which should be visible
	 * @param x     x float of the Position
	 * @param y     y float of the Position
	 */
	public QuestDummy(Quest quest, float x, float y) {
		super(x, y);
		this.quest = quest;
		this.animationHandler.addAsDefaultAnimation("default", 1, 1,
				Quest.QUEST_TEXTURE_PATH + quest.getTexture() + ".png", -1);
	}

	@Override
	public boolean deleteable() {
		return isAccept;
	}

	@Override
	protected void updateLogic() {
		super.updateLogic();
		if (isActive) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
				PlayerInteractDummyQuestEvent questEvent = (PlayerInteractDummyQuestEvent) this
						.fireEvent(new PlayerInteractDummyQuestEvent(PlayerInteractDummyQuestEvent.EVENT_ID, this.player, quest));
				questEvent.setQuest();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
				setActive(false);
			}
		}
	}

	/**
	 * Sets whether the quest can be accepted or rejected
	 * 
	 * @param isActive if true the quest can be decline or accept
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setActive(boolean isActive, Player p) {
		this.isActive = isActive;
		this.player = p;
	}

	/**
	 * 
	 * @return the actual real quest of the doll
	 */
	public Quest getQuest() {
		return this.quest;
	}

	/**
	 * if set the quest becomes active and the doll disappears
	 */
	public void setAccept() {
		this.isAccept = true;
	}
}
