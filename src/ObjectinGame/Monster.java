package ObjectinGame;

import ObjectinGame.*;
import GamePlay.*;

import javax.swing.*;
import java.awt.*;

public abstract class Monster extends JPanel {
    protected int HP;
    protected int maxHP;
    protected int speed;
    protected int armor;
    protected int reward;
    protected Image im, imR, imL;
    protected int extraMove;
    protected Point pos;
    protected int numMoveLeft;

    protected int[][] checker =new int[12][16];

    public Monster(Point pos){
        this.pos = pos;
        im = imL;
        numMoveLeft = 0;
    }
    public Point getPosition() {
        return this.pos;
    }
    public Point getCentre() {
        return new Point(this.pos.getX()+32, this.pos.getY()+32);
    }

    public void paint(Graphics g) {
        g.drawImage(im, pos.getX(), pos.getY(), 64, 64, this);
        drawHealthBar(g);
        if (GameFrame.Debug == GameFrame.Debuging.ON)
            g.drawRect(pos.getX(), pos.getY(), 64, 64);
    }

    private enum MonsterMove {UP, DOWN, LEFT, RIGHT;}
    private MonsterMove previousMove ;

    protected void moveLeft() {
        this.im = this.imL;
        this.pos.setX(this.pos.getX() - this.speed);
    }
    protected void moveRight() {
        this.im = this.imR;
        this.pos.setX(this.pos.getX() + this.speed);
    }
    protected void moveUp() {
        this.pos.setY(this.pos.getY() - this.speed);
    }
    protected void moveDown() {
        this.pos.setY(this.pos.getY() + this.speed);
    }

    private void doMove(MonsterMove mv) {
        switch (mv) {
            case UP: moveUp(); break;
            case DOWN: moveDown(); break;
            case LEFT: moveLeft(); break;
            case RIGHT: moveRight(); break;
        }
    }

    public void move() throws Exception {

        if (MapControl.target.isTouched(this)) return;

        if (numMoveLeft-- > 0) {
            doMove(previousMove);
            System.out.println("monster.java[move] "+numMoveLeft);
        }
        else {
            try {
                int j = (this.pos.getX() + 32) / 64;
                int i = (this.pos.getY() + 32) / 64;

                if (MapControl.mapper[i + 1].charAt(j) != '0' && checker[i + 1][j] == 0) {
                    doMove(MonsterMove.DOWN);
                    previousMove = MonsterMove.DOWN;
                } else if ((i > 0 && MapControl.mapper[i - 1].charAt(j) != '0' && checker[i - 1][j] == 0)) {
                    doMove(MonsterMove.UP);
                    previousMove = MonsterMove.UP;
                } else if (MapControl.mapper[i].charAt(j + 1) != '0' && checker[i][j + 1] == 0) {
                    doMove(MonsterMove.RIGHT);
                    previousMove = MonsterMove.RIGHT;
                } else if (MapControl.mapper[i].charAt(j - 1) != '0' && checker[i][j - 1] == 0) {
                    doMove(MonsterMove.LEFT);
                    previousMove = MonsterMove.LEFT;
                }
                numMoveLeft = extraMove;
                checker[i][j] = 1;
            } catch (Exception e) {
                System.out.println("monster.java[move]" + e.getMessage());
            }
        }
    }
    public void Remove() {

        //SoundLoader.play("death.wav");

        Player.monsters.remove(this);

        if (Player.monsters.isEmpty()) {
            Player.bullets.clear();
            GameFrame.GAME_LEVEL++;
            GameFrame.gameState = GameFrame.GameState.STARTING;
        }

        for (Bullet bullet: Player.bullets) {
            if (bullet.getTarget() == this) {
                bullet.setTarget(Player.monsters.get(Player.monsters.size()-1));
                bullet.setTo(Player.monsters.get(Player.monsters.size()-1).pos);
            }
        }
    }
    public void damage(int damage) {
        this.HP -= (damage - this.armor);
        if (HP <=0) {
            this.pos = null;
            this.Remove();
            Player.Money += this.reward;
        }

        //SoundLoader.play("hit.wav");
    }
    public void drawHealthBar(Graphics g) {
        final int barMaxWidth = 48;
        int barHealth = (int)Math.ceil(((double)this.HP / this.maxHP) * barMaxWidth);

        g.setColor(Color.gray);
        g.fillRect(getPosition().getX() + 12,
                getPosition().getY(), barMaxWidth, 5);
        g.setColor(Color.blue);
        g.fillRect((getPosition().getX() + 12) ,
                getPosition().getY(), barHealth, 5);

    }
    public void setIm(Image im) {
        this.im = im;
    }
}
