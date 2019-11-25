//another GameMain
package GamePlay;

import ObjectinGame.*;
import ObjectinGame.Point;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class GameFrame extends JFrame implements MouseListener, KeyListener {

    public static Debuging Debug = Debuging.OFF;
    public static boolean mute;
    public static GameFrame gameFrame;
    public static Player player;
    public static final int WINDOW_HEIGHT = 640;
    public static final int WINDOW_WITH   = 1324;
    public static int GAME_LEVEL = 1;
    public static Tower holdingTower;
    public static boolean pause;

    public static Rectangle2D.Double basicTowerArea         = new Rectangle2D.Double(1060,225,64,64);
    public static Rectangle2D.Double advanceTowerArea       = new Rectangle2D.Double(1060,225+64+5,64,64);
    public static Rectangle2D.Double knightTrapTowerArea    = new Rectangle2D.Double(1060,245+128,64,64);
    public static Rectangle2D.Double sellingTowerArea       = new Rectangle2D.Double(1180, 120, 64, 64);

    public static Rectangle2D.Double RestartButton
            = new Rectangle2D.Double(GameFrame.WINDOW_WITH/2 + 256,
            GameFrame.WINDOW_HEIGHT/2, 128, 64);
    public static Rectangle2D.Double quitButton
            = new Rectangle2D.Double(GameFrame.WINDOW_WITH/2 - 256 - 100,
            GameFrame.WINDOW_HEIGHT/2, 128, 64);

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameState == GameState.LOSING || gameState == GameState.WINNING) {
            if (quitButton.contains(new java.awt.Point(e.getX(), e.getY()))) {
                this.dispose();
            }
            if (RestartButton.contains(new java.awt.Point(e.getX(), e.getY()))); {
                gameState = GameState.STARTING;
            }
        }
        else if (gameState == GameState.STARTING) {
            gameState = GameState.PLAYING;
        }

    }
    @Override
    public void mousePressed(MouseEvent e) {
        if (basicTowerArea.contains(new java.awt.Point(e.getX(), e.getY()))) holdingTower =
                new NormalTower(new Point(0,0));
        else if (advanceTowerArea.contains(new java.awt.Point(e.getX(), e.getY()))) holdingTower =
                new SniperTower(new Point(0,0));
        else if (knightTrapTowerArea.contains(new java.awt.Point(e.getX(), e.getY()))) holdingTower =
                new MachineGunTower(new Point(0,0));
        else if (sellingTowerArea.contains(new java.awt.Point(e.getX(), e.getY()))) holdingTower =
                new TowerSell();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        int x = (e.getX()/64)*64;
        int y = (e.getY()/64)*64;

        if (holdingTower instanceof TowerSell) {
            Point p = new Point(x, y);
            for (Tower tower: Player.towers) {
                if (tower.getPos().equal(p)) {
                    Player.Money += tower.getPrice()*0.5;
                    Player.towers.remove(tower);
                    holdingTower = null;
                }
            }
        }
        else if (holdingTower != null )
            holdingTower.setPos(x, y);
        try {
            if (MapControl.mapper[y / 64].charAt(x / 64) == '0') {
                if (Player.Money - holdingTower.getPrice() >= 0) {
                    Player.towers.add(holdingTower);
                    Player.Money -= holdingTower.getPrice();
                }
                holdingTower = null;
            }
        } catch (Exception exception) {
            holdingTower =null;
        }

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pause = !pause;
        }
        else if (e.isControlDown() && e.isAltDown()) {
            if (e.getKeyCode() == KeyEvent.VK_D) {
                if (Debug == Debuging.ON) Debug = Debuging.OFF;
                else Debug = Debuging.ON;
            }
            if (e.getKeyCode() == KeyEvent.VK_M) {
                mute = !mute;
            }
            if (e.getKeyCode() == KeyEvent.VK_R) {
                gameState = GameState.STARTING;
            }
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                this.dispose();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public enum GameState  {
        STARTING,
        PLAYING,
        WINNING,
        LOSING
    }

    public enum Debuging {
        ON,
        OFF
    }

    public static GameState gameState;

    public GameFrame() throws HeadlessException, IOException {
        setSize(WINDOW_WITH, WINDOW_HEIGHT);
        setTitle("Tower Defense");
        setLocationRelativeTo(null);
        //setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        gameState = GameState.STARTING;

        addMouseListener(this);
        addKeyListener(this);

        player = new Player();
        add(player);

    }

    public static void main(String[] args) throws IOException {
        gameFrame = new GameFrame();
        gameFrame.setVisible(true);
    }
}
