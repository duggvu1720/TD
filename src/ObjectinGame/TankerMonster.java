package ObjectinGame;

import ObjectinGame.*;

public class TankerMonster extends Monster {
    public TankerMonster(Point pos) {
        super(pos);
        this.imL    = ImgLoad.monster1L;
        this.imR    = ImgLoad.monster1R;
        this.im     = imL;
        this.HP     = 3000;
        this.speed  = 1;
        this.armor  = 20;
        this.reward = 50;
        this.maxHP  = HP;
        this.extraMove = 32;
    }


}
