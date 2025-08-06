package Server.Controllers.FinalControllers;

import Client.Assets.NpcAsset;
import Client.Assets.NpcHouseAsset;
import Common.Models.App;
import Common.Models.Map;
import Common.Models.NPC.NPC;
import Common.Models.Place.NpcHosue;
import Common.Models.PlayerStuff.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

public class NpcController {

    private final NpcHouseAsset npcHouseAsset = new NpcHouseAsset();
    private final NpcAsset npcAsset = new NpcAsset();

    private float stateTime = 0f; // For animation playback

    public void update(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime(); // Assumes you're tracking deltaTime globally or passing it

        for (NPC npc : App.getInstance().getCurrentGame().getNpcs()) {
            TextureRegion[][] region;

            switch (npc.getName()) {
                case "Abigel":
                    region = npcHouseAsset.getAbigalHouse();
                    break;
                case "Harvey":
                    region = npcHouseAsset.getHarveyHouse();
                    break;
                case "Lia":
                    region = npcHouseAsset.getLiaHouse();
                    break;
                case "Robbin":
                    region = npcHouseAsset.getRobbinHouse();
                    break;
                case "Sebastian":
                    region = npcHouseAsset.getSebastianHouse();
                    break;
                default:
                    throw new RuntimeException("Unknown NPC house: " + npc.getName());
            }

            NpcHosue house = npc.getHosue();
            int houseWidth = house.getHouseWidth();
            int houseHeight = house.getHouseHeight();

            // Draw the house
            for (int row = 0; row < houseHeight; row++) {
                for (int col = 0; col < houseWidth; col++) {
                    int drawX = (house.getPosition().getY() + col) * Map.tileSize;
                    int drawY = (house.getPosition().getX() + row) * Map.tileSize;

                    int regionRow = houseHeight - 1 - row;

                    if (regionRow < region.length && col < region[0].length) {
                        batch.draw(region[regionRow][col], drawX, drawY);
                    }
                }
            }

            // === DRAW NPC SPRITE ===
            String npcKey = npc.getName().toLowerCase(); // match naming convention
            Animation<TextureRegion>[] animations = npcAsset.getAnimationsFor(npcKey);
            if (animations != null && animations.length > 0) {
                Animation<TextureRegion> animation = animations[0]; // default to animation 0 for now
                TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

                int npcDrawX = npc.getPosition().getY() * Map.tileSize;
                int npcDrawY = npc.getPosition().getX() * Map.tileSize;

                batch.draw(currentFrame, npcDrawX, npcDrawY, Player.PLAYER_WIDTH,Player.PLAYER_HEIGHT);
            }
        }
    }
}
