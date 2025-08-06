package Common.Models.Planets;

import Client.Assets.TreesAsset;
import Common.Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Fruit implements Item {
    private FruitType fruitType;
    private int numberOfFruit;
    private TreesAsset treesAsset;

    public Fruit(FruitType fruitType, int numberOfFruit) {
        this.fruitType = fruitType;
        this.numberOfFruit = numberOfFruit;
        this.treesAsset = new TreesAsset();
    }

    @Override
    public String getName() {
        return fruitType.getName();
    }

    @Override
    public Sprite show() {
        for(TreeCropType treeCropType : TreeCropType.values()){
            if(treeCropType.getFruitType().equals(fruitType)){
                return treesAsset.getFruitSprite(treeCropType.getFruitType().getName());
            }
        }
        return null;
    }

    @Override
    public int getNumber() {
        return numberOfFruit;
    }

    @Override
    public void setNumber(int number) {
        numberOfFruit = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Fruit(fruitType, number);
    }
}
