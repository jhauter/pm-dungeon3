package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class BossCutsceneHandler implements EntityEventHandler {

    private Vector2 bossPosition;
    private boolean played = false;

    public BossCutsceneHandler(Vector2 bossPosition) {
        this.bossPosition = bossPosition;
    }

    public void playCutscene() {
        if(played) {
            return;
        }

        var hero = DungeonStart.getDungeonMain().getPlayer();
        var cam = DungeonStart.getDungeonMain().getCamera();

        DungeonStart.getDungeonMain().setCameraTarget(null);
        //cam.setFocusPoint(new Point(hero.getX(), hero.getY()));
        cam.zoom = 1.5f;

        //TODO (Jonathan): Prevent player from moving
        //TODO(Jonathan):  Lerp Camera towards boss
        //TODO: Print Bossname
        //TODO: Relase after few ms
        //(OPTIONAL) Adjust zoom for boss
    }


    @Override
    public void handleEvent(EntityEvent event) {
        //? (Jonathan): We listen to only one possible event here. Is it even necessary to check the event-id?
        if(event.getEntity().getDisplayName().equals("Knight")) {
            System.out.println("Moooin");
            playCutscene();
            played = true;
        }
    }
}
