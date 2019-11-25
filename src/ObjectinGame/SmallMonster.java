package ObjectinGame;

import ObjectinGame.*;

public class SmallMonster extends Monster {
    public SmallMonster(Point pos) {
        super(pos);
        this.imL    = ImgLoad.monster3L;
        this.imR    = ImgLoad.monster3R;
        this.im     = imR;
        this.HP     = 500;
        this.speed  = 3;
        this.armor  = 0;
        this.reward = 10;
        this.maxHP  = HP;
        this.extraMove = 10;
    }


}
