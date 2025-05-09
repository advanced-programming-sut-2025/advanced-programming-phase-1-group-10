package Models.Planets;

import Models.Item;

public class Seed implements Item {
    private SeedType seedType;
    private int numberOfSeed;


    @Override
    public String getName() {
        return seedType.getName();
    }

    @Override
    public String getSymbol() {
        return "s";
    }

    @Override
    public int getNumber() {
        return numberOfSeed;
    }

    @Override
    public void setNumber(int number){
        numberOfSeed = number;
    }
}
