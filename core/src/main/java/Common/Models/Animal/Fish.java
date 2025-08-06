package Common.Models.Animal;

import Client.Assets.FishAsset;
import Common.Models.Item;
import Common.Models.Tools.Quality;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Fish implements Item {

    private FishType fishType;
    private int numberOfFish;
    protected FishAsset fishAsset;
    private Quality quality ;

    public Fish(FishType fishType, int numberOfFish) {
        this.fishType = fishType;
        this.numberOfFish = numberOfFish;
        this.fishAsset = new FishAsset();
        this.quality = Quality.STARTER;
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

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public Quality getQuality() {
        return quality;
    }
}
