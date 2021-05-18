package de.fhbielefeld.pmdungeon.quibble.entity.range_combat;

public enum ProjectileTypes {
	
	SPELL_ICE_BLAST("Spell Ice Blast", Projectile.PROJECTILE_PATH + "iceBlast_right_anim_f.png");

	String name;
	String path;
	
	ProjectileTypes(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	

}
