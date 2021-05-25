package de.fhbielefeld.pmdungeon.quibble.input;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

/**
 * An KeyList Contains an ArrayList of all Keys thats necessary to play the full
 * game <br>
 * Provides a default Configuration and could be changed if wanted
 * 
 * <blockquote>
 * <li>up <code>KeyMovement</code> to walk up
 * <li>right <code>KeyMovement</code> to walk right
 * <li>down <code>KeyMovement</code> to walk down
 * <li>left <code>KeyMovement</code> to walk left </blockquote> <blockquote>
 * <li>useItem <code>KeyMouseButton</code> to use a selected Item </blockquote>
 * <blockquote>
 * <li>openChest <code>KeyJustPressed</code> to open a Chest
 * <li>closeCestInv <code>KeyJustPressed</code> to close a Chest
 * <li>pickupDrop <code>KeyJustPressed</code> to pickup drops from the ground
 * <li>items <code>Key's JustPressed in an ArrayList</code> which represents the
 * Inventory Slots and supposed to select an Item </blockquote>
 */
public class KeyList {
	
	public static final String OPEN_CHEST = "OpenChest";
	public static final String USE_ITEM = "UseItem";
	public static final String PICK_UP_DROP = "PickUpDrop";
	public static final String CLOSE_CHEST_INV = "CloseChestInv";

	public static final String[] SELECT_ITEM = new String[9];
	{
	  for(int i = 0; i < SELECT_ITEM.length; ++i)
	  {
	    SELECT_ITEM[i] = "select_" + i;
	  }
	}
	

	private ArrayList<Key> keyList;

	public KeyList() {
		keyList = new ArrayList<>();
		this.keyList.add(up());
		this.keyList.add(right());
		this.keyList.add(down());
		this.keyList.add(left());
		this.keyList.add(openChest());
		this.keyList.add(useItem());
		this.keyList.addAll(items());
		this.keyList.add(pickUpDrop());
		this.keyList.add(closeChestInv());
	}

	/**
	 * This have to be a <code>KeyMovement</code> because its carries a Vector for
	 * movement calculation
	 * 
	 * @return a KeyMovement Key which supposed to let the Player go up
	 */
	KeyMovement up() {
		return new KeyMovement("up", Keys.W, 0, 1);
	}

	/**
	 * This have to be a <code>KeyMovement</code> because its carries a Vector for
	 * movement calculation
	 * 
	 * @return a KeyMovement Key which supposed to let the Player go right
	 */
	KeyMovement right() {
		return new KeyMovement("right", Keys.D, 1, 0);
	}

	/**
	 * This have to be a <code>KeyMovement</code> because its carries a Vector for
	 * movement calculation
	 * 
	 * @return a KeyMovement Key which supposed to let the Player go down
	 */
	KeyMovement down() {
		return new KeyMovement("down", Keys.S, 0, -1);
	}

	/**
	 * This have to be a <code>KeyMovement</code> because its carries a Vector for
	 * movement calculation
	 * 
	 * @return a KeyMovement Key which supposed to let the Player go left
	 */
	KeyMovement left() {
		return new KeyMovement("left", Keys.A, -1, 0);
	}

	/**
	 * This have to be a <code>KeyMouseButton</code> because the ranged Combat
	 * System require a Mouse Click
	 * 
	 * @return Key Mouse Button which is supposed to use if a selected Item should
	 *         be used
	 */
	KeyMouseButton useItem() {
		return new KeyMouseButton(USE_ITEM, Buttons.LEFT);
	}

	/**
	 * 
	 * @return a KeyJustPressed which is supposed to open a Chest
	 */
	KeyJustPressed openChest() {
		return new KeyJustPressed(OPEN_CHEST, Keys.E);
	}

	/**
	 * 
	 * @return a KeyJustPressed which is supposed to pick up a drop from Ground
	 */
	KeyJustPressed pickUpDrop() {
		return new KeyJustPressed(PICK_UP_DROP, Keys.SPACE);
	}

	/**
	 * 
	 * @return a KeyJustPressed which is supposed to close a Chest Inventory HUD
	 */
	KeyJustPressed closeChestInv() {
		return new KeyJustPressed(CLOSE_CHEST_INV, Keys.SPACE);
	}

	/**
	 * 
	 * @return a List of KeysJustPressed which used to select Items
	 */
	public ArrayList<KeyJustPressed> items() {
		ArrayList<KeyJustPressed> keys = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			KeyJustPressed k = new KeyJustPressed(SELECT_ITEM[i], 7 + i);
			keys.add(k);
		}
		return keys;
	}

	public ArrayList<Key> getKeyList() {
		return keyList;
	}

}
