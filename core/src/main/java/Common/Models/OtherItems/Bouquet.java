package Common.Models.OtherItems;

import Client.Assets.OtherAsset;
import Common.Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bouquet implements Item {

    public Bouquet() {
        setNumber(1);
    }

    @Override
    public String getName() {
        return "Bouquet";
    }

    @Override
    public Sprite show() {
        return OtherAsset.getBouquet();
    }

    @Override
    public int getNumber() {
        return 1;
    }

    @Override
    public void setNumber(int number) {

    }

    @Override
    public Item copyItem(int number) {
        return new Bouquet();
    }
}
