package Client.Assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class OtherAsset {

    private final static Sprite ring = new Sprite(new Texture("otherItems/Ring.png"));
    private final static Sprite bouquet = new Sprite(new Texture("otherItems/Bouquet.png"));

    public static Sprite getRing() {
        return ring;
    }

    public static Sprite getBouquet() {
        return bouquet;
    }
}
