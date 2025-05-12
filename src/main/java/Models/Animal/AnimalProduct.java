package Models.Animal;

import Models.Item;

public class AnimalProduct implements Item {

    private AnimalProductType animalProductType;
    private int numberOfCooking;

    public AnimalProduct(AnimalProductType animalProductType, int numberOfCooking) {
        this.animalProductType = animalProductType;
        this.numberOfCooking = numberOfCooking;
    }

    @Override
    public String getName() {
        return animalProductType.getName();
    }

    @Override
    public String getSymbol() {
        return "u";
    }

    @Override
    public void setNumber(int number) {
        this.numberOfCooking = number;
    }

    @Override
    public int getNumber() {
        return this.numberOfCooking;
    }


}
