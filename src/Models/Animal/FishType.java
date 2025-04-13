package src.Models.Animal;
import src.Models.DateTime.Season;

public enum FishType {

    SALMON(Season.Autumn, 75),
    SARDINE(Season.Autumn, 40),
    SHAD(Season.Autumn, 60),
    BLUE_DISCUS(Season.Autumn, 120),
    MIDNIGHT_CARP(Season.Winter, 150),
    SQUID(Season.Winter, 80),
    TUNA(Season.Winter, 100),
    PERCH(Season.Winter, 55),
    FLOUNDER(Season.Spring, 100),
    LIONFISH(Season.Spring, 100),
    HERRING(Season.Spring, 30),
    GHOSTFISH(Season.Spring, 45),
    TILAPIA(Season.Summer, 75),
    DORADO(Season.Summer, 100),
    SUNFISH(Season.Summer, 30),
    RAINBOW_TROUT(Season.Summer, 65),

    LEGEND(Season.Spring, 5000),
    GLACIERFISH(Season.Winter, 1000),
    ANGLER(Season.Autumn, 900),
    CRIMSONFISH(Season.Summer, 1500);


    private Season season ;
    private int price ;

    FishType(Season season, int price) {
        this.season = season;
        this.price = price;
    }
}
