package Models.Planets;

import Models.DateTime.Season;
import Models.Item;

public enum FruitType {
    APRICOT("Apricot",1,59,true,38,Season.SPRING),
    CHERRY("Cherry",1,80,true,38,Season.SPRING),
    BANANA("Banana",1,150,true,75,Season.SUMMER),
    MANGO("Mango",1,130,true,100,Season.SUMMER),
    ORANGE("Orange",1,100,true,38,Season.SUMMER),
    PEACH("Peach",1,140,true,38,Season.SUMMER),
    APPLE("Apple",1,100,true,38,Season.FALL),
    POMEGRANATE("Pomegranate",1,140,true,38,Season.FALL),
    OAK_RESIN("Oak Resin",7,150,false,0,null),
    MAPLE_SYRUP("Maple Syrup",9,200,false,0,null),
    PINE_TAR("Pine Tar",5,100,false,0,null),
    SAP("Sap",1,2,true,-2,null),
    COMMON_MUSHROOM("Common Mushroom",1,40,true,38,null),
    MYSTIC_SYRUP("Mystic Syrup",7,1000,true,500,null),
    ;

    private String name;
    private int harvestCycle;
    private int baseSellPrice;
    private boolean isEdible;
    private int energy;
    private Season season;

    FruitType(String name,int harvestCycle, int baseSellPrice, boolean isEdible, int energy, Season season) {
        this.name = name;
        this.baseSellPrice = baseSellPrice;
        this.harvestCycle = harvestCycle;
        this.isEdible = isEdible;
        this.energy = energy;
        this.season = season;
    }
}
