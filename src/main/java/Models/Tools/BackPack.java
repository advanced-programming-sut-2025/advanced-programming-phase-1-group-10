package Models.Tools;

import Models.Item;

import java.util.ArrayList;

public class BackPack {

    private BackpackType backpackType;
    private final ArrayList<Item> items = new ArrayList<Item>();


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
        if (items.size() < backpackType.getCapacity()) {
            for(Item it : items){
                if(it.getName().equals(item.getName())){
                    it.setNumber(item.getNumber() + item.getNumber());
                    return true;
                }
            }
            items.add(item);
            return true;
        } else {
            //Backpack is full
            return false;
        }
    }

    public boolean removeItem(Item item) {
        if (items.contains(item)) {
            items.remove(item);
            return true;
        } else {
            //Item not found in backpack
            return false;
        }
    }

    public boolean setItemNumber(Item item, int number) {
        for (Item i : items) {
            if (i.equals(item)) {
                i.setNumber(number);
                return true;
            }
        }
        //Item not found in backpack
        return false;
    }

    public int getItemNumber(Item item) {
        for (Item i : items) {
            if (i.equals(item)) {
                return i.getNumber();
            }
        }
        //Item not found in backpack
        return -1;
    }

}
