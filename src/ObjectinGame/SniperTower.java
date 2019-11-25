package ObjectinGame;

import ObjectinGame.*;
import GamePlay.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SniperTower extends Tower {

    public SniperTower(Point pos) {
        super(pos, "res/Map/basic_tower.png");
        this.price = 100;
        this.range = 64*8;
        this.damage = 200;

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
