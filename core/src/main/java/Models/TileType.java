package Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public enum TileType {
    Wall(new Sprite(new Texture("tiles/grass.png")),false),
    Grass(new Sprite(new Texture("tiles/grass.png")),true),
    Plowed(new Sprite(new Texture("tiles/plowed.png")),true),
    Watered(new Sprite(new Texture("tiles/watered.png")),true),
    Lake(new Sprite(new Texture("tiles/lake.png")),false),
    Quarry(new Sprite(new Texture("tiles/quarry.png")),true);

    private final Sprite sprite;
    private final boolean isWalkable;

    TileType(Sprite sprite, boolean isWalkable) {
        this.sprite = sprite;
        this.isWalkable = isWalkable;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isWalkable() {
        return isWalkable;
    }
}
