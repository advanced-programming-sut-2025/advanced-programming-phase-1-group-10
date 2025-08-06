package Common.Models.PlayerStuff;


import Common.Models.Tools.BackPack;
import Common.Models.Tools.BackpackType;
import Common.Models.Tools.Refrigerator;

public class Inventory {
    private BackPack backPack;
    private Refrigerator refrigerator;

    Inventory(){
        this.backPack = new BackPack();
        this.backPack.setBackpackType(BackpackType.STARTER);
        this.refrigerator = new Refrigerator(100);
    }

    public BackPack getBackPack() {
        return backPack;
    }

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }
}
