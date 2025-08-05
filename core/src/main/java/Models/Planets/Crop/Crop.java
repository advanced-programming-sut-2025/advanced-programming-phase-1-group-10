package Models.Planets.Crop;

import Assets.CropAsset;
import Assets.ForagingCropAsset;
import Models.DateTime.DateTime;
import Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Crop implements Item {
    CropType cropType;
    private int numberOfCrop;
    private int currentStageIndex;
    private DateTime lastWateredDate;
    private DateTime whenPlanted;
    private boolean isFertilized;
    private boolean isHarvestable;
    private boolean wasHarvested;
    private int daysSinceLastHarvest;
    private static ForagingCropAsset foragingCropAsset;
    private static CropAsset cropAsset;
    private boolean showCrop;

    static {
        foragingCropAsset = new ForagingCropAsset();
        cropAsset = new CropAsset();
    }

    public Crop(CropType cropType, int numberOfCrop) {
        this.cropType = cropType;
        this.numberOfCrop = numberOfCrop;
        this.currentStageIndex = 0;
        this.isFertilized = false;
        this.isHarvestable = false;
        this.wasHarvested = false;
        this.daysSinceLastHarvest = 0;
        this.showCrop = true;
    }

    @Override
    public String getName() {
        return cropType.getName();
    }

    @Override
    public Sprite show() {
        if (cropType instanceof ForagingCropType) {
            return foragingCropAsset.getCropSprite((ForagingCropType) cropType);
        } else if (cropType instanceof CropTypeNormal) {
            CropTypeNormal normalCrop = (CropTypeNormal) cropType;
            if (isHarvestable) {
                Sprite fruitSprite = cropAsset.getFruitSprite(cropType.getName());
                if (fruitSprite != null) {
                    return fruitSprite;
                }
            }

            if (currentStageIndex == 0) {
                return cropAsset.getSeedSprite(normalCrop.getSource().getName());
            }

            return cropAsset.getCropStageSprite(cropType.getName(), currentStageIndex);
        }
        return null;
    }

    public void updateGrowth() {
        if (whenPlanted == null) return;
        DateTime currentDate = Models.App.getInstance().getCurrentGame().getGameTime();
        int daysPassed = calculateDaysDifference(whenPlanted, currentDate);
        if (wasHarvested && cropType instanceof CropTypeNormal) {
            CropTypeNormal normalCrop = (CropTypeNormal) cropType;
            if (normalCrop.isOneTime()) {
                return;
            }
            daysSinceLastHarvest++;

            if (daysSinceLastHarvest >= normalCrop.getRegrowthTime()) {
                isHarvestable = true;
                System.out.println(cropType.getName() + " is ready for harvest again!");
            }
            return;
        }

        if (cropType instanceof CropTypeNormal) {
            CropTypeNormal normalCrop = (CropTypeNormal) cropType;
            ArrayList<Integer> stages = normalCrop.getCropTypes();
            int totalDays = 0;
            int newStage = 0;

            for (int i = 0; i < stages.size() && newStage < stages.size(); i++) {
                totalDays += stages.get(i);

                if (daysPassed >= totalDays) {
                    newStage = i + 1;
                } else {
                    break;
                }
            }

            if (newStage >= stages.size()) {
                isHarvestable = true;
            }

            if (newStage > currentStageIndex) {
                currentStageIndex = newStage;
                System.out.println(cropType.getName() + " grew to stage " + currentStageIndex);
            }
        }
    }

    public Item harvestCrop() {
        if (isHarvestable && cropType instanceof CropTypeNormal) {
            CropTypeNormal normalCrop = (CropTypeNormal) cropType;
            CropFruit harvestedFruit = new CropFruit(cropType, 1);

            if (normalCrop.isOneTime()) {
                showCrop = false;
                return harvestedFruit;
            } else {
                wasHarvested = true;
                isHarvestable = false;
                daysSinceLastHarvest = 0;
                return harvestedFruit;
            }
        }

        return null;
    }

    private int calculateDaysDifference(DateTime startDate, DateTime endDate) {
        int startTotalDays = startDate.getYear() * 12 * 28 + (startDate.getMonth() - 1) * 28 + startDate.getDay();
        int endTotalDays = endDate.getYear() * 12 * 28 + (endDate.getMonth() - 1) * 28 + endDate.getDay();
        return endTotalDays - startTotalDays;
    }

    public void renderAt(SpriteBatch batch, int tileY, int tileX) {
        Sprite cropSprite = show();
        if (cropSprite != null) {
            float x = tileX * 32;
            float y = tileY * 32;
            cropSprite.setSize(32, 32);
            float offsetX = (cropSprite.getWidth() - 32) / 2;
            float offsetY = (cropSprite.getHeight() - 32) / 2;
            cropSprite.setPosition(x - offsetX, y - offsetY);
            if(showCrop)
                cropSprite.draw(batch);
        }
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

    public void setShowCrop(boolean showCrop) {
        this.showCrop = showCrop;
    }

    public void setWhenPlanted(DateTime whenPlanted) {
        this.whenPlanted = whenPlanted;
    }
}
