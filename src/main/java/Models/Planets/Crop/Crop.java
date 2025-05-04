package Models.Planets.Crop;

import Models.Item;

public class Crop implements Item {
    CropType cropType;
    private int numberOfCrop;

    @Override
    public String getName() {
        return cropType.getName();
    }

    @Override
    public int getNumber() {
        return numberOfCrop;
    }

    @Override
    public void setNumber(int number) {
        numberOfCrop = number;
    }
}
