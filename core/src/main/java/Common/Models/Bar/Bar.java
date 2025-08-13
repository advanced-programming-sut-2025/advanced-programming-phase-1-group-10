package Common.Models.Bar;

import Common.Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bar implements Item {

    private BarType barType;
    private int numberOfBar;

    public Bar(BarType barType, int numberOfBar) {
        this.barType = barType;
        this.numberOfBar = numberOfBar;
    }

    @Override
    public String getName(){
        return barType.getName();
    }

    @Override
    public Sprite show() {
        return barType.getSprite();
    }

    @Override
    public int getNumber() {
        return numberOfBar;
    }

    @Override
    public void setNumber(int number) {
        numberOfBar = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Bar(barType, number);
    }


}
