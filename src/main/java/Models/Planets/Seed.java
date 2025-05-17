package Models.Planets;

import Models.Item;

public class Seed implements Item {
    private SeedType seedType;
    private int numberOfSeed;

    public Seed(SeedType seedType, int numberOfSeed) {
        this.seedType = seedType;
        this.numberOfSeed = numberOfSeed;
    }

    @Override
    public String getName() {
        return seedType.getName();
    }

    @Override
    public String getSymbol() {
        return "Se";
    }

    @Override
    public int getNumber() {
        return numberOfSeed;
    }

    @Override
    public void setNumber(int number){
        numberOfSeed = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Seed(seedType, number);
    }
}
