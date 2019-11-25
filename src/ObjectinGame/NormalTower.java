package ObjectinGame;

import ObjectinGame.*;
import GamePlay.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NormalTower extends Tower {

    public NormalTower(Point pos) {
        super(pos, "res/Map/advance_tower.png");
        this.price = 50;
        this.range = 64*5;
        this.damage = 100;

    }


    public void fire() {
        long timeNow = System.currentTimeMillis();
        if (timeNow - lastFired >= 500) {
            lastFired = timeNow;
            List<Monster> monsters = Player.monsters;
            for (Monster mon: Player.monsters) {
                if (distance(mon.getCentre(), this.pos) < (double) range - 80) {

                    //SoundLoader.play("fireshoot.wav");

                    Player.bullets.add(new ArrowBullet(
                            new Point(this.pos.getX() + 32, this.pos.getY() + 32),
                            mon.getPosition(),
                            mon,
                            this.damage
                    ));
                    break;
                }
            }
        }
    }
}
