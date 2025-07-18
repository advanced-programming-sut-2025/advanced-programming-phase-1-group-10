package Models.Mineral;

import Models.Item;

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
    public String getSymbol() {
        return "Mi";
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
