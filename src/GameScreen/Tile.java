package GameScreen;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {
    Image im;

    public Tile() {
        im = new ImageIcon("res/Tile/tile3.png").getImage();
    }
    public void paint(Graphics g) {
        g.drawImage(im, 1024, 0, this);
    }

}
