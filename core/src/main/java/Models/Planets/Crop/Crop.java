package Models.Planets.Crop;

import Models.DateTime.DateTime;
import Models.Item;

public class Crop implements Item {
    CropType cropType;
    private int numberOfCrop;
    private int currentStageIndex;
    private DateTime lastWateredDate;
    private DateTime whenPlanted;
    private boolean isFertilized;
    private boolean isHarvestable;

    public Crop(CropType cropType, int numberOfCrop) {
        this.cropType = cropType;
        this.numberOfCrop = numberOfCrop;
        this.currentStageIndex = 0;
        this.isFertilized = false;
        this.isHarvestable = false;
    }

    @Override
    public String getName() {
        return cropType.getName();
    }

    @Override
    public String getSymbol() {
        return "Cr";
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

    public CropType getCropType() {
        return cropType;
    }

    public int getCurrentStageIndex() {
        return currentStageIndex;
    }

    public void setCurrentStageIndex(int currentStageIndex) {
        this.currentStageIndex = currentStageIndex;
    }

    public DateTime getLastWateredDate() {
        return lastWateredDate;
    }

    public void setLastWateredDate(DateTime lastWateredDate) {
        this.lastWateredDate = lastWateredDate;
    }

    public boolean isFertilized() {
        return isFertilized;
    }

    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }

    public boolean isHarvestable() {
        return isHarvestable;
    }

    public void setHarvestable(boolean harvestable) {
        isHarvestable = harvestable;
    }

    public DateTime getWhenPlanted() {
        return whenPlanted;
    }

    public void setWhenPlanted(DateTime whenPlanted) {
        this.whenPlanted = whenPlanted;
    }
}
