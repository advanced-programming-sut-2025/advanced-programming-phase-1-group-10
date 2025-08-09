package Common.Models.OtherItems;

import Client.Assets.OtherAsset;
import Common.Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ring implements Item {
    @Override
    public String getName() {
        return "Ring";
    }

    @Override
    public Sprite show() {
        return OtherAsset.getRing();
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
        return new Ring();
    }
}
