package Common.Models.Artisan;

import Common.Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ArtisanProduct implements Item {

    private ArtisanProductType artisanProductType;
    private int numberOfArtisan;

    public ArtisanProduct(ArtisanProductType artisanProductType, int numberOfArtisan) {
        this.artisanProductType = artisanProductType;
        this.numberOfArtisan = numberOfArtisan;
    }

    @Override
    public String getName() {
        return artisanProductType.getName();
    }

    @Override
    public Sprite show() {
        return null;
    }

    @Override
    public int getNumber() {
        return numberOfArtisan;
    }

    @Override
    public void setNumber(int number) {
        numberOfArtisan = number;
    }

    @Override
    public Item copyItem(int number) {
        return new ArtisanProduct(artisanProductType, number);
    }
}
