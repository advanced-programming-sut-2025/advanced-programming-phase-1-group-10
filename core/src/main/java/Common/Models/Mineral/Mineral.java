package Common.Models.Mineral;

import Common.Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Mineral implements Item {

    private final MineralTypes mineralType;
    private int numberOfMineral;
    private boolean isAxed;

    public Mineral(MineralTypes mineralType, int numberOfMineral) {
        this.mineralType = mineralType;
        this.numberOfMineral = numberOfMineral;
        this.isAxed = true;
    }


    @Override
    public String getName() {
        return mineralType.getName();
    }

    @Override
    public Sprite show() {
        return isAxed ? mineralType.getOre() : mineralType.getNode();
    }

    @Override
    public int getNumber() {
        return numberOfMineral;
    }

    @Override
    public void setNumber(int number) {
        numberOfMineral = number;
    }

    @Override
    public Item copyItem(int number) {
        Mineral mineral = new Mineral(mineralType, number);
        mineral.setAxed(true);
        return mineral;
    }

    public boolean isAxed() {
        return isAxed;
    }

    public void setAxed(boolean axed) {
        isAxed = axed;
    }

}
