package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

/**
 * An KeyList Contains an ArrayList of all Keys thats necessary to play the full
 * game <br>
 * Provides a default Configuration and could be changed if wanted
 * 
 * Each Key could be Could be overwritten
 * 
 * @param up           <code>KeyMovement</code> to walk up
 * @param right        <code>KeyMovement</code> to walk right
 * @param down         <code>KeyMovement</code> to walk down
 * @param left         <code>KeyMovement</code> to walk left
 *                     <p>
 * @param useItem      <code>KeyMouseButton</code> to use a selected Item
 *                     <p>
 * @param openChest    <code>Key</code> to open a Chest
 * @param closeCestInv <code>Key</code> to close a Chest
 * @param pickupDrop   <code>Key</code> to pickup drops from the ground
 * @param items        <code>Key's in an ArrayList</code> which represents the
 *                     Inventory Slots and supposed to select an Item
 */
public class KeyList
{
	
	private ArrayList<Key> keyList;
	
	public KeyList()
	{
		keyList = new ArrayList<>();
		this.keyList.add(up());
		this.keyList.add(right());
		this.keyList.add(down());
		this.keyList.add(left());
		this.keyList.add(interact());
		this.keyList.add(useItem());
		this.keyList.addAll(items());
		this.keyList.add(pickUpDrop());
		this.keyList.add(close());
		this.keyList.add(acceptQuest());
		this.keyList.add(declineQuest());
	}
	
	/**
	 * This have to be a <code>KeyMovement</code> because its carries a Vector for
	 * movement calculation
	 * 
	 * @return a KeyMovement Key which supposed to let the Player go up
	 */
	protected Key up()
	{
		return new Key("up", Keys.W, Key.KEY_PRESSED);
	}
	
	/**
	 * This have to be a <code>KeyMovement</code> because its carries a Vector for
	 * movement calculation
	 * 
	 * @return a KeyMovement Key which supposed to let the Player go right
	 */
	protected Key right()
	{
		return new Key("right", Keys.D, Key.KEY_PRESSED);
	}
	
	/**
	 * This have to be a <code>KeyMovement</code> because its carries a Vector for
	 * movement calculation
	 * 
	 * @return a KeyMovement Key which supposed to let the Player go down
	 */
	protected Key down()
	{
		return new Key("down", Keys.S, Key.KEY_PRESSED);
	}
	
	/**
	 * This have to be a <code>KeyMovement</code> because its carries a Vector for
	 * movement calculation
	 * 
	 * @return a KeyMovement Key which supposed to let the Player go left
	 */
	protected Key left()
	{
		return new Key("left", Keys.A, Key.KEY_PRESSED);
	}
	
	/**
	 * This have to be a <code>KeyMouseButton</code> because the ranged Combat
	 * System require a Mouse Click
	 * 
	 * @return Key Mouse Button which is supposed to use if a selected Item should
	 *         be used
	 */
	protected Key useItem()
	{
		return new Key("use_item", Buttons.LEFT, Key.MOUSE_CLICKED);
	}
	
	/**
	 * 
	 * @return a KeyJustPressed which is supposed to open a Chest
	 */
	protected Key interact()
	{
		return new Key("interact", Keys.E, Key.KEY_JUST_PRESSED);
	}
	
	/**
	 * 
	 * @return a KeyJustPressed which is supposed to pick up a drop from Ground
	 */
	protected Key pickUpDrop()
	{
		return new Key("pick_up_drop", Keys.SPACE, Key.KEY_JUST_PRESSED);
	}
	
	/**
	 * 
	 * @return a KeyJustPressed which is supposed to close a Chest Inventory HUD
	 */
	protected Key close()
	{
		return new Key("close", Keys.ESCAPE, Key.KEY_JUST_PRESSED);
	}
	
	protected Key acceptQuest()
	{
		return new Key("quest_accept", Keys.J, Key.KEY_JUST_PRESSED);
	}
	
	protected Key declineQuest()
	{
		return new Key("quest_decline", Keys.N, Key.KEY_JUST_PRESSED);
	}
	
	/**
	 * 
	 * @return a List of KeysJustPressed which used to select Items
	 */
	protected ArrayList<Key> items()
	{
		ArrayList<Key> keys = new ArrayList<>();
		for(int i = 0; i < 9; i++)
		{
			Key k = new Key("select_item_" + i, Keys.NUM_1 + i, Key.KEY_JUST_PRESSED);
			keys.add(k);
		}
		return keys;
	}
	
	public ArrayList<Key> getKeyList()
	{
		return keyList;
	}
	
}
