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
    private static ForagingCropAsset foragingCropAsset;
    private static CropAsset cropAsset;

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

            if (currentStageIndex == 0) {
                CropTypeNormal normalCrop = (CropTypeNormal) cropType;
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

    public void setWhenPlanted(DateTime whenPlanted) {
        this.whenPlanted = whenPlanted;
    }

    public static void disposeAssets() {
        if (foragingCropAsset != null) {
            foragingCropAsset.dispose();
            foragingCropAsset = null;
        }
        if (cropAsset != null) {
            cropAsset.dispose();
            cropAsset = null;
        }
    }
}
