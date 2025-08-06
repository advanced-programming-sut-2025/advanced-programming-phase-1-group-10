package Common.Models.Tools;

import Common.Models.Item;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Wood implements Item {
    private int numberOfWood;
    private final Sprite woodSprite = new Sprite(new Texture("tools/Wood.png"));

    public Wood(int numberOfWood) {
        this.numberOfWood = numberOfWood;
    }

    @Override
    public String getName() {
        return "Wood";
    }

    @Override
    public Sprite show() {
        return woodSprite;
    }

    @Override
    public int getNumber() {
        return numberOfWood;
    }

    @Override
    public void setNumber(int number) {
        this.numberOfWood = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Wood(number);
    }
}
