package ObjectinGame;

import javax.swing.*;
import java.awt.*;

public class Road extends Map {

    public Road(Point pos) {
        ImageIcon icon =  new ImageIcon("res/map/road.png");
        this.im = icon.getImage();
        this.pos    = pos;
    }

    public void paint(Graphics g) {
        g.drawImage(this.im, this.pos.getX(), this.pos.getY(),64,64, this);
    }
}