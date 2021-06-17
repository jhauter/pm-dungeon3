package de.fhbielefeld.pmdungeon.quibble.boss;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;

import java.util.List;

public class ProjectileBossAction extends BossAction {

    //NOTE: Die Positionen der Projectiles sollen Boss-Relative sein. Ist noch unschön und net eindeutig
    List<ProjectileSpawner> spawnerList;
    private BossBattle battle;

    public ProjectileBossAction(List<ProjectileSpawner> spawnerList) {
        this.spawnerList = spawnerList;

        for(var i : spawnerList) {
            i.setPosition(new Vector2(BossBattle.boss.getPosition().x + i.getX(), BossBattle.boss.getY() + i.getY()));
        }

    }

    @Override
    public void onActionBegin(BossBattle battle) {
        System.out.println("OnActionBegin");
        this.battle = battle;

        for(var i : spawnerList) {
            battle.level.spawnEntity(i);
        }
    }

    @Override
    public void execute() {
        //Hmmm?
    }

    @Override
    public void onActionEnd() {
        for(var i: spawnerList) {
            i.despawnFlag = false;
        }
    }
}
