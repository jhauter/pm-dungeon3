package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;

import java.util.List;

public class ProjectileBossAction extends BossAction {

    //NOTE: Die Positionen der Projectiles sollen Boss-Relative sein. Ist noch unsch�n und net eindeutig
    List<ProjectileSpawner> spawnerList;
    private BossBattle battle;

    public ProjectileBossAction(List<ProjectileSpawner> spawnerList) {
        this.spawnerList = spawnerList;

        this.duration = 100;
        this.cooldown = 100;

        for(var i : spawnerList) {
            i.setPosition(new Vector2(BossBattle.boss.getPosition().x + i.getX(), BossBattle.boss.getY() + i.getY()));
        }

    }

    @Override
    public void onActionBegin(BossBattle battle) {
        super.onActionBegin(battle);
        System.out.println("OnActionBegin");
        this.battle = battle;

        for(var i : spawnerList) {
            System.out.println("Spawn");
            i.despawnFlag = false;
            battle.level.spawnEntity(i);
        }
    }

    @Override
    public void execute() {
        //Hmmm?
    }

    @Override
    public void onActionEnd() {
        System.out.println("OnActionEnd");
        for(var i: spawnerList) {
            System.out.println("Despawn");
            i.despawnFlag = true;
        }
    }
}
