package Models.Mineral;

import Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Mineral implements Item {

    private MineralTypes mineralType;
    private int numberOfMineral;

    public Mineral(MineralTypes mineralType, int numberOfMineral) {
        this.mineralType = mineralType;
        this.numberOfMineral = numberOfMineral;
    }


    @Override
    public String getName() {
        return mineralType.getName();
    }

    @Override
    public Sprite show() {
        return null;
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
        return new Mineral(mineralType, number);
    }


}
