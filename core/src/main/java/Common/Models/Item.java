package Common.Models;


import com.badlogic.gdx.graphics.g2d.Sprite;

public interface Item{

    String getName();
    Sprite show();
    int getNumber();
    void setNumber(int number);
    Item copyItem(int number);

}
