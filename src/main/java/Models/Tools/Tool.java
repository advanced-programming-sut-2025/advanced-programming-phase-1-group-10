package Models.Tools;

import Models.Item;
import Models.Result;
import Models.Tile;

public abstract class Tool implements Item {
    private String name;
    private int energyUsage;
    private Quality quality;

    public Tool(Quality quality, int energyUsage) {
        this.quality = quality;
        this.energyUsage = energyUsage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSymbol() {
        return "t";
    }

    @Override
    public int getNumber() {
        return 1;
    }

    @Override
    public void setNumber(int number) {
        //Must not be coded, because number of tool is constant (one per player);
    }

    @Override
    public Item copyItem(int number) {
        //Tool mustn't copy
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnergyUsage() {
        return energyUsage - quality.getValue();
    }

    public void setEnergyUsage(int energyUsage) {
        this.energyUsage = energyUsage;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public void use(Tile tile) {

    }

}
