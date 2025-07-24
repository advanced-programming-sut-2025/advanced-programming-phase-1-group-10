package Controllers.FinalControllers;

import Assets.SlotAsset;
import Models.App;
import Models.Item;
import Models.PlayerStuff.Player;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class InventoryBarController {

    private final SlotAsset slotAsset;
    private final static int SLOT_SIZE = 64;
    private final Player player = App.getInstance().getCurrentGame().getCurrentPlayer();

    public InventoryBarController() {
        this.slotAsset = new SlotAsset();
    }

    public void update(SpriteBatch batch) {
        int screenWidth = com.badlogic.gdx.Gdx.graphics.getWidth();
        int totalBarWidth = Player.PLAYER_INENTORY_BAR_SIZE * SLOT_SIZE;
        int startX = (screenWidth - totalBarWidth) / 2;
        int y = 40;

        for (int i = 0; i < Player.PLAYER_INENTORY_BAR_SIZE; i++) {
            TextureRegion texture = (i == player.getSelectedSlot()) ?
                slotAsset.getSlotHover() : slotAsset.getSlot();
            batch.draw(texture, startX + i * SLOT_SIZE, y, SLOT_SIZE, SLOT_SIZE);

            Item item = null;
            if (i < player.getIventoryBarItems().size()) {
                item = player.getIventoryBarItems().get(i);
            }

            if (item != null) {
                batch.draw(item.show(), startX + i * SLOT_SIZE, y, SLOT_SIZE, SLOT_SIZE);
            }
        }

    }

    // Scroll input: +1 = next slot, -1 = previous slot
    public void scrollSlot(int direction) {
        int current = player.getSelectedSlot();
        int max = Player.PLAYER_INENTORY_BAR_SIZE;
        current = (current + direction + max) % max;
        player.setSelectedSlot(current);
    }

    // Number key input (e.g., Input.Keys.NUM_1)
    public void selectSlotByKey(int keycode) {
        int index = switch (keycode) {
            case Input.Keys.NUM_1 -> 0;
            case Input.Keys.NUM_2 -> 1;
            case Input.Keys.NUM_3 -> 2;
            case Input.Keys.NUM_4 -> 3;
            case Input.Keys.NUM_5 -> 4;
            case Input.Keys.NUM_6 -> 5;
            case Input.Keys.NUM_7 -> 6;
            case Input.Keys.NUM_8 -> 7;
            case Input.Keys.NUM_9 -> 8;
            case Input.Keys.NUM_0 -> 9;
            case Input.Keys.MINUS -> 10;
            case Input.Keys.EQUALS -> 11;
            default -> -1;
        };
        if (index >= 0 && index < Player.PLAYER_INENTORY_BAR_SIZE) {
            player.setSelectedSlot(index);
        }
    }
}
