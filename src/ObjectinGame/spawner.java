package ObjectinGame;

import javax.swing.*;
import java.awt.*;

public class spawner extends Map {

    public spawner(Point pos) {
        ImageIcon icon =  new ImageIcon("res/map/spaner.png");
        this.im = icon.getImage();
        this.pos    = pos;
    }

    public void paint(Graphics g) {
        g.drawImage(this.im, this.pos.getX(), this.pos.getY(), 64,64, this);
    }

    public Point getPos() {
        return new Point(this.pos.getX(), this.pos.getY());
    }
}