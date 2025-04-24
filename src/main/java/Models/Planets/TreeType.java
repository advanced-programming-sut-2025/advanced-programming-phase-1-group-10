package Models.Planets;

import Models.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TreeType{
    APRICOT_TREE("Apricot",FruitType.APRICOT),
    CHERRY_TREE("Cherry",FruitType.CHERRY),
    BANANA_TREE("Banana",FruitType.BANANA),
    MANGO_TREE("Mango",FruitType.MANGO),
    ORANGE_TREE("Orange",FruitType.ORANGE),
    PEACH_TREE("Peach",FruitType.PEACH),
    APPLE_TREE("Apple",FruitType.APPLE),
    POMEGRANATE_TREE("Pomegranate",FruitType.POMEGRANATE),
    OAK_TREE("Oak Tree",FruitType.OAK_RESIN),
    MAPLE_TREE("Maple",FruitType.MAPLE_SYRUP),
    PINE_TREE("Pine",FruitType.PINE_TAR),
    MAHOGANY_TREE("Mahogany",FruitType.SAP),
    MUSHROOM_TREE("Mushroom",FruitType.COMMON_MUSHROOM),
    MYSTIC_TREE("Mystic",FruitType.MYSTIC_SYRUP),
    ;
    private String name;
    private final ArrayList<Integer> items = new ArrayList<>(Arrays.asList(7, 7, 7, 7));
    private FruitType fruitType;

    TreeType(String name, FruitType fruitType) {
        this.name = name;
        this.fruitType = fruitType;
    }
}
