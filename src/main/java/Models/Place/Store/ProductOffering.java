package Models.Place.Store;

import Models.Animal.Animal;
import Models.Item;
import Models.Place.Place;
import Models.PlayerStuff.Player;
import Models.Tools.Tool;

public class ProductOffering {
    private String type;
    private final Item item;
    private final Place place;
    private final Animal animal;
    private final Tool tool;
    private final String name;
    private final int price;           // قیمت در این فروشگاه
    private final int dailyLimit;      // حداکثر تعداد قابل فروش در روز
    private int soldToday;             // برای پیگیری تعداد فروخته‌شده امروز


    public ProductOffering(String type, Item item, Place place, Animal animal,Tool tool,String name, int price, int dailyLimit) {
        this.type = type;
        this.item = item;
        this.place = place;
        this.animal = animal;
        this.tool = tool;
        this.name = name;
        this.price = price;
        this.dailyLimit = dailyLimit;
        this.soldToday = 0;
    }

    public String getType() {return type;}
    public Item getItem() { return item; }
    public Place getPlace() { return place; }
    public Animal getAnimal() { return animal; }
    public Tool getTool() { return tool; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getDailyLimit() { return dailyLimit; }
    public int getSoldToday() { return soldToday; }


    public boolean canPurchase(int count, Player player) {
        // ۱) محدودیت روزانه
        if (soldToday + count > dailyLimit) return false;

        return true;
    }

    public void purchase(int count) {
        soldToday += count;
    }

    public void resetDaily() {
        soldToday = 0;
    }
}
