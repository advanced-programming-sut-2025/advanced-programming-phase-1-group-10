package Controllers.Utils;

import Models.Item;
import Models.PlayerStuff.Player;
import Models.Tools.BackPack;
import Models.App;

import java.util.ArrayList;



public class InventoryUtils {

    public static void transferItem(int fromIndex, boolean fromBackpack) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        BackPack backPack = player.getInventory().getBackPack();
        ArrayList<Item> hotbar = player.getIventoryBarItems();

        if (fromBackpack) {
            // Move from backpack to hotbar
            if (fromIndex >= 0 && fromIndex < backPack.getItems().size()) {
                Item item = backPack.getItems().get(fromIndex);
                if (item == null) return;

                // Try to add to first empty slot in hotbar
                if (hotbar.size() < Player.PLAYER_INENTORY_BAR_SIZE) {
                    hotbar.add(item);
                    backPack.getItems().remove(fromIndex);
                } else {
                    for (int i = 0; i < hotbar.size(); i++) {
                        if (hotbar.get(i) == null) {
                            hotbar.set(i, item);
                            backPack.getItems().remove(fromIndex);
                            break;
                        }
                    }
                }
            }

        } else {
            // Move from hotbar to backpack
            if (fromIndex >= 0 && fromIndex < hotbar.size()) {
                Item item = hotbar.get(fromIndex);
                if (item == null) return;

                // Try to add to backpack if space
                if (backPack.getItems().size() < backPack.getBackpackType().getCapacity()) {
                    backPack.getItems().add(item);
                    hotbar.set(fromIndex, null);
                }
            }
        }
    }
}
