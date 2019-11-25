package ObjectinGame;

import ObjectinGame.*;

public class NormalMonster extends Monster{
    public NormalMonster(Point pos) {
        super(pos);
        this.imR        = ImgLoad.monster2R;
        this.imL        = ImgLoad.monster2L;
        this.im         = imR;
        this.HP         = 1000;
        this.speed      = 2;
        this.armor      = 10;
        this.reward     = 30;
        this.maxHP      = HP;
        this.extraMove  = 16;
    }
}
