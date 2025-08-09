package Client.Controllers.Utils;

import Common.Models.Item;
import Common.Models.Mineral.Mineral;
import Common.Models.Mineral.MineralTypes;
import Common.Models.Bar.Bar;
import Common.Models.Bar.BarType;
import Common.Models.Animal.Fish;
import Common.Models.Animal.FishType;
import Common.Models.Animal.AnimalProduct;
import Common.Models.Animal.AnimalProductType;
import Common.Models.OtherItems.Bouquet;
import Common.Models.OtherItems.Ring;
import Common.Models.Planets.Fruit;
import Common.Models.Planets.FruitType;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class ItemUtility {

    private static final Map<String, Function<Integer, Item>> ITEM_CREATORS = new HashMap<>();

    static {
        // Minerals
        for (MineralTypes type : MineralTypes.values()) {
            ITEM_CREATORS.put(type.getName().toLowerCase(Locale.ROOT),
                number -> new Mineral(type, number));
        }

        // Bars
        for (BarType type : BarType.values()) {
            ITEM_CREATORS.put(type.getName().toLowerCase(Locale.ROOT),
                number -> new Bar(type, number));
        }

        // Fish
        for (FishType type : FishType.values()) {
            ITEM_CREATORS.put(type.getName().toLowerCase(Locale.ROOT),
                number -> new Fish(type, number));
        }

        // Animal products
        for (AnimalProductType type : AnimalProductType.values()) {
            ITEM_CREATORS.put(type.getName().toLowerCase(Locale.ROOT),
                number -> new AnimalProduct(type, number));
        }

        // Fruits
        for (FruitType type : FruitType.values()) {
            ITEM_CREATORS.put(type.getName().toLowerCase(Locale.ROOT),
                number -> new Fruit(type, number));
        }

        // Other items
        ITEM_CREATORS.put("bouquet", number -> new Bouquet());
        ITEM_CREATORS.put("ring", number -> new Ring());
    }

    public static Item createItem(String name, int number) {
        Function<Integer, Item> creator = ITEM_CREATORS.get(name.toLowerCase(Locale.ROOT));
        if (creator != null) {
            return creator.apply(number);
        }
        throw new IllegalArgumentException("Unknown item name: " + name);
    }
}
