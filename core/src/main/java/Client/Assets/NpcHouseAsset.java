package Client.Assets;

import Common.Models.Map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NpcHouseAsset {

    private final TextureRegion[][] abigalHouse;
    private final TextureRegion[][] harveyHouse;
    private final TextureRegion[][] liaHouse;
    private final TextureRegion[][] robbinHouse;
    private final TextureRegion[][] sebastianHouse;

    public NpcHouseAsset() {
        this.abigalHouse = TextureRegion.split(new Texture("npchouses/abigel.png"), Map.tileSize,  Map.tileSize);
        this.harveyHouse = TextureRegion.split(new Texture("npchouses/harvey.png"), Map.tileSize,  Map.tileSize);
        this.liaHouse = TextureRegion.split(new Texture("npchouses/lia.png"), Map.tileSize,  Map.tileSize);
        this.robbinHouse = TextureRegion.split(new Texture("npchouses/robbin.png"), Map.tileSize,  Map.tileSize);
        this.sebastianHouse = TextureRegion.split(new Texture("npchouses/sebastian.png"), Map.tileSize,  Map.tileSize);
    }

    public TextureRegion[][] getAbigalHouse() {
        return abigalHouse;
    }

    public TextureRegion[][] getHarveyHouse() {
        return harveyHouse;
    }

    public TextureRegion[][] getLiaHouse() {
        return liaHouse;
    }

    public TextureRegion[][] getRobbinHouse() {
        return robbinHouse;
    }

    public TextureRegion[][] getSebastianHouse() {
        return sebastianHouse;
    }
}
