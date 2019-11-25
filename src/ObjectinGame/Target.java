package ObjectinGame;

import ObjectinGame.*;
import GamePlay.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Target extends Map {


    public Target(Point pos) {
        ImageIcon icon =  new ImageIcon("res/map/target.png");
        this.im = icon.getImage();
        this.pos    = pos;
    }

    public void paint(Graphics g) {
        g.drawImage(this.im, this.pos.getX(), this.pos.getY(),64,64, this);
    }

    public boolean isTouched(Monster mon) throws Exception{
        if (mon == null) return false;
        if (mon.getPosition() == Point.ErrPoint) return false;

        Rectangle2D.Double targetArea = new Rectangle2D.Double(
                mon.getPosition().getX(),mon.getPosition().getY(),64,64
        );
        if (targetArea.contains(new java.awt.Point(this.pos.getX(), this.pos.getY())) ||
                targetArea.contains(new java.awt.Point(this.pos.getX()+64, this.pos.getY()+64)) ||
                targetArea.contains(new java.awt.Point(this.pos.getX()+64, this.pos.getY())) ||
                targetArea.contains(new java.awt.Point(this.pos.getX(), this.pos.getY()+64))
        ) {
            Player.Heart--;
            mon.Remove();
            if (Player.Heart == 0 ) {
                Thread.sleep(1000);
                GameFrame.gameState = GameFrame.GameState.LOSING;
            }
            return true;
        }
        return false;
    }
}
