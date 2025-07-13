package Models.PlayerStuff;


import Models.Tools.BackPack;
import Models.Tools.BackpackType;
import Models.Tools.Refrigerator;

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
