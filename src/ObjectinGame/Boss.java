package ObjectinGame;

import ObjectinGame.*;

public class Boss extends Monster {
    public Boss(Point pos) {
        super(pos);
        this.im     = ImgLoad.bossR;
        this.imR    = im;
        this.imL    = ImgLoad.bossL;
        this.HP     = 6000;
        this.speed  = 1;
        this.armor  = 15;
        this.reward = 100;
        this.maxHP  = HP;
        this.extraMove = 32;
    }

}