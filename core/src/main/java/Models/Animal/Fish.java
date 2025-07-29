package Models.Animal;

import Assets.FishAsset;
import Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Fish implements Item {

    private FishType fishType;
    private int numberOfFish;
    protected FishAsset fishAsset;

    public Fish(FishType fishType, int numberOfFish) {
        this.fishType = fishType;
        this.numberOfFish = numberOfFish;
        this.fishAsset = new FishAsset();
    }

    @Override
    public String getName() {
        return fishType.getName();
    }

    @Override
    public Sprite show() {
        return fishAsset.show(fishType);
    }

    @Override
    public int getNumber() {
        return numberOfFish;
    }

    @Override
    public void setNumber(int number) {
        numberOfFish = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Fish(fishType, number);
    }
}
