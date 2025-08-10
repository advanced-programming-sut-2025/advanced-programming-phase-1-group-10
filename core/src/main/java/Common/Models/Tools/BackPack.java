package Common.Models.Tools;

import Common.Models.App;
import Common.Models.Item;
import Common.Models.PlayerStuff.Player;

import java.util.ArrayList;

public class BackPack {

    private BackpackType backpackType;
    private final ArrayList<Item> items = new ArrayList<>();

    public BackpackType getBackpackType() {
        return backpackType;
    }

    public ArrayList<Item> getItems() {
        return items;
    }


    public void setBackpackType(BackpackType backpackType) {
        this.backpackType = backpackType;
    }

    public boolean addItem(Item item) {
        final ArrayList<Item> barItems = App.getInstance().getCurrentGame().getCurrentPlayer().getIventoryBarItems();
        final int barCapacity = Player.PLAYER_INENTORY_BAR_SIZE;
        final int backpackCapacity = backpackType.capacity;

        // 1. Try to stack in bar
        for (Item barItem : barItems) {
            if (barItem == null) continue;
            if (barItem.getName().equals(item.getName())) {
                barItem.setNumber(barItem.getNumber() + item.getNumber());
                return true;
            }
        }

        // 2. Try to stack in backpack
        for (Item backpackItem : items) {
            if (backpackItem.getName().equals(item.getName())) {
                backpackItem.setNumber(backpackItem.getNumber() + item.getNumber());
                return true;
            }
        }

        // 3. Try to add to empty slot in bar
        if (barItems.size() < barCapacity) {
            barItems.add(item);
            return true;
        }

        // 4. Try to add to empty slot in backpack
        if (items.size() < backpackCapacity) {
            items.add(item);
            return true;
        }

        // 5. No space
        return false;
    }


    public void removeItem(Item item) {
        items.remove(item);
    }

    public boolean removeItemNumber(String name, int number) {
        for (Item it : items) {
            if (it.getName().equals(name)) {
                if (it.getNumber() <= number) items.remove(it);
                else it.setNumber(it.getNumber() - number);
                return true;
            }
        }
        return false;
    }

    public void setItemNumber(Item item, int number) {
        for (Item i : items) {
            if (i.equals(item)) {
                i.setNumber(number);
                return;
            }
        }
        //Item not found in backpack
    }

    public int getItemNumber(Item item) {
        for (Item i : items) {
            if (i.equals(item)) {
                return i.getNumber();
            }
        }
        //Item not found in backpack
        return 0;
    }

    public boolean hasItem(Item item, int number) {
        int count = 0;

        // Check backpack
        for (Item i : items) {
            if (i != null && i.getName().equals(item.getName())) {
                count += i.getNumber();
            }
        }

        // Check inventory bar
        for (Item i : App.getInstance().getCurrentGame().getCurrentPlayer().getIventoryBarItems()) {
            if (i != null && i.getName().equals(item.getName())) {
                count += i.getNumber();
            }
        }

        return count >= number;
    }


}
