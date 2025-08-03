package Models.Planets;

import java.util.ArrayList;
import java.util.Arrays;

public enum TreeType{
    APRICOT_TREE("Apricot",FruitType.APRICOT,false),
    CHERRY_TREE("Cherry",FruitType.CHERRY,false),
    BANANA_TREE("Banana",FruitType.BANANA,false),
    MANGO_TREE("Mango",FruitType.MANGO,false),
    ORANGE_TREE("Orange",FruitType.ORANGE,false),
    PEACH_TREE("Peach",FruitType.PEACH,false),
    APPLE_TREE("Apple",FruitType.APPLE,false),
    POMEGRANATE_TREE("Pomegranate",FruitType.POMEGRANATE,false),
    OAK_TREE("Oak",FruitType.OAK_RESIN,true),
    MAPLE_TREE("Maple",FruitType.MAPLE_SYRUP,true),
    PINE_TREE("Pine",FruitType.PINE_TAR,true),
    MAHOGANY_TREE("Mahogany",FruitType.SAP,true),
    MUSHROOM_TREE("Mushroom",FruitType.COMMON_MUSHROOM,true),
    MYSTIC_TREE("Mystic",FruitType.MYSTIC_SYRUP,false),
    ;
    private final String name;
    private final ArrayList<Integer> items = new ArrayList<>(Arrays.asList(7, 7, 7, 7));
    private final FruitType fruitType;
    private final boolean isForaging;

    TreeType(String name, FruitType fruitType, boolean isForaging) {
        this.name = name;
        this.fruitType = fruitType;
        this.isForaging = isForaging;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getItems() {
        return items;
    }

    public FruitType getFruitType() {
        return fruitType;
    }

    public boolean isForaging() {
        return isForaging;
    }
}
