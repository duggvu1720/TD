package ObjectinGame;

import ObjectinGame.*;
import GamePlay.*;

import javax.swing.*;
import java.awt.*;

public class FireBullet extends Bullet{


    public FireBullet(Point from, Point to, Monster target, int power) {
        super(from, to, target, power);
        this.im = new ImageIcon("res/Bullet/Fireball.png").getImage();
        this.speed = 5;

        //SoundLoader.play("fireball.wav");
    }
    public void paint(Graphics g) {
        g.drawImage(im, pos.getX(), pos.getY(), 25,15,this);

        if (GameFrame.Debug == GameFrame.Debuging.ON)
            g.drawOval(
                    this.pos.getX() -16,
                    this.pos.getY() -16,
                    64, 64
            );

    }
}
