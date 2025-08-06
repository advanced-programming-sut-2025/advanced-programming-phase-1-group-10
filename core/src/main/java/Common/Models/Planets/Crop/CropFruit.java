package Common.Models.Planets.Crop;

import Client.Assets.CropAsset;
import Common.Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CropFruit implements Item {
    private CropType cropType;
    private int numberOfFruits;
    private static CropAsset cropAsset;

    static {
        if (cropAsset == null) {
            cropAsset = new CropAsset();
        }
    }

    public CropFruit(CropType cropType, int numberOfFruits) {
        this.cropType = cropType;
        this.numberOfFruits = numberOfFruits;
    }

    @Override
    public String getName() {
        return cropType.getName() + " Fruit";
    }

    @Override
    public Sprite show() {
        return cropAsset.getFruitSprite(cropType.getName());
    }

    @Override
    public int getNumber() {
        return numberOfFruits;
    }

    @Override
    public void setNumber(int number) {
        this.numberOfFruits = number;
    }

    @Override
    public Item copyItem(int number) {
        return new CropFruit(cropType, number);
    }

    public CropType getCropType() {
        return cropType;
    }
}
