package Models.Planets.Crop;

import Models.Item;

public class Crop implements Item {
    CropType cropType;
    private int numberOfCrop;

    public Crop(CropType cropType, int numberOfCrop) {
        this.cropType = cropType;
        this.numberOfCrop = numberOfCrop;
    }

    @Override
    public String getName() {
        return cropType.getName();
    }

    @Override
    public String getSymbol() {
        return "M";
    }

    @Override
    public int getNumber() {
        return numberOfCrop;
    }

    @Override
    public void setNumber(int number) {
        numberOfCrop = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Crop(cropType, number);
    }
}
