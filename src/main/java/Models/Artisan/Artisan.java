package Models.Artisan;

import Models.Item;

public class Artisan implements Item {

    private ArtisanType artisanType;
    private int numberOfArtisan;

    @Override
    public String getName() {
        return artisanType.getName();
    }

    @Override
    public String getSymbol() {
        return "a";
    }

    @Override
    public int getNumber() {
        return numberOfArtisan;
    }

    @Override
    public void setNumber(int number) {
        numberOfArtisan = number;
    }

}
