package Common.Models.Tools;

import Common.Models.Item;

import java.util.ArrayList;

public class Refrigerator {

    private final ArrayList<Item> items = new ArrayList<Item>();
    private final int capacity;

    public Refrigerator(int capacity){
        this.capacity = capacity;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean addItem(Item item) {
        if (items.size() < capacity) {
            for(Item it : items){
                if(it.getName().equals(item.getName())){
                    it.setNumber(it.getNumber() + item.getNumber());
                    return true;
                }
            }
            items.add(item);
            return true;
        }else if (items.size()==capacity){
            for(Item it : items){
                if(it.getName().equals(item.getName())){
                    it.setNumber(it.getNumber() + item.getNumber());
                    return true;
                }
            }
            return false;
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
            //Item not found
            return false;
        }
    }

    public boolean removeItemNumber(String name, int number) {
        for(Item it : items){
            if(it.getName().equals(name)){
                if(it.getNumber() <= number) items.remove(it); else it.setNumber(it.getNumber() - number);
                return true;
            }
        }
        return false;
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
        return 0;
    }
}
