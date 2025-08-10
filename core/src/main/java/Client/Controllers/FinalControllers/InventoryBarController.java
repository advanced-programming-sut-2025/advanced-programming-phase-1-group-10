package Client.Controllers.FinalControllers;

import Client.Assets.SlotAsset;
import Client.Controllers.Utils.InventoryUtils;
import Client.Network.ClientNetworkManager;
import Common.Models.App;
import Common.Models.Item;
import Common.Models.PlayerStuff.Player;
import Common.Network.Messages.MessageTypes.ChangeItemTradeMessage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

public class InventoryBarController {

    private final SlotAsset slotAsset;
    private final static int SLOT_SIZE = 64;
    private Player player;

    private final NpcMenuController npcMenuController;
    private final FriendshipController friendshipController;
    private final TradeController tradeController;

    public InventoryBarController(NpcMenuController npcMenuController, FriendshipController friendshipController, TradeController tradeController) {
        this.slotAsset = new SlotAsset();
        this.npcMenuController = npcMenuController;
        this.friendshipController = friendshipController;
        this.player = App.getInstance().getCurrentGame().getCurrentPlayer();
        this.tradeController = tradeController;
    }

    public void update(SpriteBatch batch) {
        // refresh player each frame (in case it was switched)
        player = App.getInstance().getCurrentGame().getCurrentPlayer();

        int screenWidth = Gdx.graphics.getWidth();
        int totalBarWidth = Player.PLAYER_INENTORY_BAR_SIZE * SLOT_SIZE;
        int startX = (screenWidth - totalBarWidth) / 2;
        int y = 40;

        List<Item> barItems = player.getIventoryBarItems();

        BitmapFont font = new BitmapFont(); // consider caching instead of recreating/disposing
        font.getData().setScale(1);
        font.setColor(Color.WHITE);

        // Draw bar
        for (int i = 0; i < Player.PLAYER_INENTORY_BAR_SIZE; i++) {
            TextureRegion texture = (i == player.getSelectedSlot())
                ? slotAsset.getSlotHover()
                : slotAsset.getSlot();
            int slotX = startX + i * SLOT_SIZE;

            batch.draw(texture, slotX, y, SLOT_SIZE, SLOT_SIZE);

            Item item = (i < barItems.size()) ? barItems.get(i) : null;
            if (item != null) {
                batch.draw(item.show(), slotX, y, SLOT_SIZE, SLOT_SIZE);

                if (item.getNumber() > 1) {
                    font.draw(batch, String.valueOf(item.getNumber()), slotX + SLOT_SIZE - 12, y + 14);
                }
            }
        }

        // Right-click logic
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // invert Y

            for (int i = 0; i < Player.PLAYER_INENTORY_BAR_SIZE; i++) {
                int slotX = startX + i * SLOT_SIZE;
                int slotY = y;

                if (mouseX >= slotX && mouseX < slotX + SLOT_SIZE &&
                    mouseY >= slotY && mouseY < slotY + SLOT_SIZE) {

                    Item selected = (i < barItems.size()) ? barItems.get(i) : null;
                    if (selected == null) {
                        continue; // empty slot, skip
                    }


                    if(tradeController.isShown()){
                        Item clone = selected.copyItem(1);
                        if(tradeController.addItem(clone)){
                            if(App.getInstance().getCurrentGame().isOnline()){
                                ClientNetworkManager.getInstance().sendMessage(new ChangeItemTradeMessage(
                                    App.getInstance().getCurrentGame().getCurrentPlayer().getName(),
                                    tradeController.getGoalPlayerName(),
                                    clone.getName(),
                                    1
                                ));
                            }
                            selected.setNumber(selected.getNumber() - 1);
                            if (selected.getNumber() <= 0) {
                                barItems.remove(i);
                            }
                        }
                    } else if (npcMenuController.isMenuOpen()) {
                        int hovered = npcMenuController.getHoveredSlotIndex();
                        if (hovered != -1) {
                            Item clone = selected.copyItem(1);
                            if (npcMenuController.tryAddItemToSlot(clone)) {
                                selected.setNumber(selected.getNumber() - 1);
                                if (selected.getNumber() <= 0) {
                                    barItems.remove(i);
                                }
                            }
                        }
                    } else if (friendshipController.isMenuOpen()) {
                        int friendSelected = friendshipController.getSelectedSlotIndex(); // must exist accessor
                        if (friendSelected != -1) {
                            Item clone = selected.copyItem(1);
                            if (friendshipController.tryAddItemToSlot(clone)) {
                                selected.setNumber(selected.getNumber() - 1);
                                if (selected.getNumber() <= 0) {
                                    barItems.remove(i);
                                }
                            }
                        }
                    } else {
                        InventoryUtils.transferItem(i, false); // bar -> backpack
                    }

                    break; // handled click
                }
            }
        }

        font.dispose();
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
