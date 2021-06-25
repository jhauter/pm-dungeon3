package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class BossCutsceneHandler implements EntityEventHandler {

    private Vector2 bossPosition;
    private Boss boss;
    private boolean played = false;
    private DungeonLevel level;
    private Player hero;

    /**
     * @param boss as target
     * @param level to get Camera
     * @param hero as target
     */
    public BossCutsceneHandler(Boss boss,DungeonLevel level,Player hero)
    {
        this.boss = boss;
        this.level = level;
        this.hero = hero;
    }

    /**
     * Spawns CutsceneCamera Entity and set boss and hero as target
     */
    public void playCutscene() {
        if(played) {
            return;
        }
        this.played = true;

        CutsceneCamera c = new CutsceneCamera(15, 23);
        //CutsceneHelper c = new CutsceneHelper(15, 23);

        level.spawnEntity(c);
        c.setTarget(boss);
        c.setHero(hero);

        //TODO: Prevent player and Boss from moving
        //TODO: Lerp! Camera towards boss
        //TODO: Print Bossname
        //TODO: (OPTIONAL) Zoom Camera
        //TODO: (OPTIONAL) Music
        //TODO: (OPTIONAL) Spawn Animation



    }

    /**
     * @param event the event object is currently not used
     */
    @Override
    public void handleEvent(EntityEvent event) {
        //? do we need this event?
        //if(event.getEntity().getDisplayName().equals("Knight")) {
        //   System.out.println("Moooin");
        //   playCutscene();
        //   played = true;
        //}
    }
}
