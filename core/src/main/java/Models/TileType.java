package Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public enum TileType {
    Wall(new Sprite(new Texture("tiles/wall.png"))),
    Grass(new Sprite(new Texture("tiles/grass.png"))),
    Plowed(new Sprite(new Texture("tiles/plowed.png"))),
    Watered(new Sprite(new Texture("tiles/watered.png"))),
    Lake(new Sprite(new Texture("tiles/lake.png"))),
    ;

    private Sprite sprite;

    TileType(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
