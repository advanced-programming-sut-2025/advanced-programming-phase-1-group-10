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
}
