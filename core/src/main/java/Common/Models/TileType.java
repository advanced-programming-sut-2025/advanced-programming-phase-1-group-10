package Common.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;

public enum TileType {
    Wall("tiles/grass.png", false),
    Grass("tiles/grass.png", true),
    Plowed("tiles/plowed.png", true),
    Watered("tiles/watered.png", true),
    Lake("tiles/lake.png", false),
    Fertilized("tiles/Fertilized.png", true),
    Quarry("tiles/quarry.png", true);

    private final String texturePath;
    private Sprite sprite;
    private final boolean isWalkable;

    TileType(String texturePath, boolean isWalkable) {
        this.texturePath = texturePath;
        this.isWalkable = isWalkable;
    }

    public Sprite getSprite() {
        if (sprite == null) {
            sprite = new Sprite(new Texture(Gdx.files.internal(texturePath)));
        }
        return sprite;
    }

    public boolean isWalkable() {
        return isWalkable;
    }
}
