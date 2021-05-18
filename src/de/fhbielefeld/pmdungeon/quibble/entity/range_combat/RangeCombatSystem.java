package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.item.ItemWeaponMagic;

public class RangeCombatSystem {

	private DungeonLevel level;
	private Player player;
	private RangedCombat util;

	private RangedCombatUtils rangeWeapon;

	private float positionX;
	private float positionY;

	public RangeCombatSystem(DungeonLevel level, RangedCombatUtils rangeWeapon, Player player) {
		this.player = player;
		this.level = level;
		this.rangeWeapon = rangeWeapon;
		// + 0.5 for not start at the Hero directly
		positionX = player.getPosition().x + 0.5f;
		positionY = player.getPosition().y + 0.5f;
	}

	public void RangedCombat() {
		if (rangeWeapon == RangedCombatUtils.SPELL_ICE_BLAST) {
			if (player.getEquippedItems().getItem(0).getItemType() instanceof ItemWeaponMagic) {
				util = new SpellIceBlast(positionX, positionY, player, 2 + player.getCurrentExpLevel());
				player.useEquippedItem(0);
				util.setVelocity(1.5f, 0);
				level.spawnEntity(util);
			}
		}
		
		
	}

}
