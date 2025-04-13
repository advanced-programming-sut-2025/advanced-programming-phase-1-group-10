package src.Models.Animal;
import src.Models.DateTime.Season;
import src.Models.Item;

public enum FishType implements Item {

    SALMON(Season.FALL, 75),
    SARDINE(Season.FALL, 40),
    SHAD(Season.FALL, 60),
    BLUE_DISCUS(Season.FALL, 120),
    MIDNIGHT_CARP(Season.WINTER, 150),
    SQUID(Season.WINTER, 80),
    TUNA(Season.WINTER, 100),
    PERCH(Season.WINTER, 55),
    FLOUNDER(Season.SPRING, 100),
    LIONFISH(Season.SPRING, 100),
    HERRING(Season.SPRING, 30),
    GHOSTFISH(Season.SPRING, 45),
    TILAPIA(Season.SUMMER, 75),
    DORADO(Season.SUMMER, 100),
    SUNFISH(Season.SUMMER, 30),
    RAINBOW_TROUT(Season.SUMMER, 65),
    LEGEND(Season.SPRING, 5000),
    GLACIERFISH(Season.WINTER, 1000),
    ANGLER(Season.FALL, 900),
    CRIMSONFISH(Season.SUMMER, 1500);


    private Season season ;
    private int price ;

    FishType(Season season, int price) {
        this.season = season;
        this.price = price;
    }
}
