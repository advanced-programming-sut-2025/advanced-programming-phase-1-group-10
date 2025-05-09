package Models.PlayerStuff;


import Models.Tools.BackPack;
import Models.Tools.BackpackType;

public class Inventory {
    private BackPack backPack;

    Inventory(){
        this.backPack = new BackPack();
        this.backPack.setBackpackType(BackpackType.STARTER);
    }

    public BackPack getBackPack() {
        return backPack;
    }
}
