package GamePlay;

import ObjectinGame.*;
import GameScreen.Tile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static GamePlay.GameFrame.pause;


public class Player  extends JPanel implements Runnable {

    public static LinkedList<Monster> monsters = new LinkedList<Monster>();
    public static LinkedList<Bullet> bullets = new LinkedList<Bullet>();
    public static LinkedList<Tower> towers = new LinkedList<Tower>();

    public static List<Map> mapper;
    public static int Money;
    public static int Heart;

    static Tile tile = new Tile();
    Thread thread;

    private long time = System.currentTimeMillis();
    private int i = 0;

    public static void loadMapper() {
        try {
            mapper = MapControl.updatePlayMapper();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Player() {
        thread = new Thread(this);
        thread.start();
        Heart = 10;

    }

    private void reset() {
        Heart = 10;
        Money = 500;
        towers.clear();
        monsters.clear();
        bullets.clear();
        i = 0;
    }

    public void paint(Graphics g){
        if (!pause) {
            try {
                if (GameFrame.gameState == GameFrame.GameState.STARTING) {
                    g.fillRect(GameFrame.WINDOW_WITH / 2 - 64,
                            GameFrame.WINDOW_HEIGHT / 2 - 32, 128, 64);
                    this.reset();
                    try {
                        loadMapper();
                    } catch (Exception e) {
                        System.out.println("player.java[paint-try]"+e.getMessage());
                    }
                    g.drawImage(new ImageIcon("res/Map/Screen/start.png").getImage(), 0, 0, this);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Consolas", Font.BOLD, 50));
                    g.drawString("Tower Defense - Level " + GameFrame.GAME_LEVEL, 350, 600);
                    //g.setFont(new Font("Consolas", Font.BOLD, 20));
                } else if (GameFrame.gameState == GameFrame.GameState.WINNING ||
                        GameFrame.gameState == GameFrame.GameState.LOSING) {
                    g.drawImage(new ImageIcon("res/Map/Screen/start.png").getImage(), 0, 0, this);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Consolas", Font.BOLD, 50));

                    if (GameFrame.gameState == GameFrame.GameState.LOSING) {
                        g.drawString("You Loosed!", 500, 600);
                    } else if (GameFrame.gameState == GameFrame.GameState.WINNING) {
                        g.drawString("You Won!", 550, 600);
                    }

                    try {
                        loadMapper();
                    } catch (Exception e) {
                        System.out.println("PLAYER: " + e.getMessage());
                    }
                    g.setColor(Color.WHITE);
                    g.fillRect(GameFrame.WINDOW_WITH / 2 - 256 - 100,
                            GameFrame.WINDOW_HEIGHT / 2, 128, 64);
                    g.fillRect(GameFrame.WINDOW_WITH / 2 + 256,
                            GameFrame.WINDOW_HEIGHT / 2, 128, 64);

                    g.setFont(new Font("Consolas", Font.BOLD, 20));
                    g.setColor(Color.black);
                    g.drawString("QUIT", GameFrame.WINDOW_WITH / 2 - 256 - 75,
                            GameFrame.WINDOW_HEIGHT / 2 + 25);
                    g.drawString("RESTART", GameFrame.WINDOW_WITH / 2 + 256 + 25,
                            GameFrame.WINDOW_HEIGHT / 2 + 25);
                } else if (GameFrame.gameState == GameFrame.GameState.PLAYING) {
                    for (Map map : mapper)
                        map.paint(g);

                    tile.paint(g);

                    for (Tower tower : towers) {
                        try {
                            tower.fire();
                            tower.paint(g);
                        } catch (Exception e) {
                            System.out.println("Player.java: " + e.getMessage());
                        }
                    }

                    for (Monster mons : monsters) {
                        try {
                            mons.move();
                            mons.paint(g);
                        } catch (Exception e) {
                            System.out.println("Player.java[mon]: " + e.getMessage());
                        }
                    }

                    for (Bullet bullet : bullets) {
                        bullet.paint(g);
                        try {
                            bullet.move();
                        } catch (Exception e) {
                            System.out.println("player.java[bullet]: " + e.getMessage());
                        }
                    }

                    if (GameFrame.holdingTower != null)
                        g.drawImage(GameFrame.holdingTower.getIm(),
                                (int) getMousePosition().getX(),
                                (int) getMousePosition().getY(),64, 64,  null);

                    g.drawImage((new ImageIcon("res/Tile/coin.gif").getImage()), 1180, 120, 64, 64,null);

                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Consolas", Font.BOLD, 20));
                    g.drawString(String.valueOf(Money), 1124, 135);
                    g.drawString(String.valueOf(Heart), 1124, 183);
                    g.drawString("50", 1124, 250);
                    g.drawString("100", 1124, 310);
                    g.drawString("200", 1124, 380);

                    if (GameFrame.Debug == GameFrame.Debuging.ON) {
                        g.drawRect(1180, 120, 64, 64);
                        g.drawRect(1060, 205 - 10, 64, 64);
                        g.drawRect(1060, 205 + 59, 64, 64);
                        g.drawRect(1060, 225 + 118, 64, 64);

                    }
                }
            } catch (Exception e) {
                System.out.println("Player.java[paint]" + e.getMessage());
            }
        }
        else {
            g.setFont(new Font("Consolas", Font.BOLD, 25));
            g.setColor(Color.WHITE);
            g.drawString("PAUSED", 1080, 600);
        }
    }

    private void createMonster(char c) {
        //System.out.println(c);
        switch (c) {
            case '1': monsters.add(new NormalMonster(MapControl.spawner.getPos())); break;
            case '2': monsters.add(new SmallMonster(MapControl.spawner.getPos())); break;
            case '3': monsters.add(new TankerMonster(MapControl.spawner.getPos())); break;
            case '4': monsters.add(new Boss(MapControl.spawner.getPos())); break;
            case ' ': break;
        }
    }

    @Override
    public void run(){

        //SoundLoader.loop("background.wav");

        while (true) {
            try {
                if (System.currentTimeMillis() - time > 1000 && i < MapControl.MonsterSpan.length()) {
                    createMonster(MapControl.MonsterSpan.charAt(i++));
                    time = System.currentTimeMillis();
                }

                repaint();

                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println("Player.java[run]" + e.getMessage());
            }
        }
    }

}

