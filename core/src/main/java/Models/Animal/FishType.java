package Models.Animal;
import Models.DateTime.Season;

public enum FishType {

    SALMON("Salmon",Season.FALL, 75),
    SARDINE("Sardine",Season.FALL, 40),
    SHAD("Shad",Season.FALL, 60),
    BLUE_DISCUS("Blue Discus",Season.FALL, 120),
    MIDNIGHT_CARP("Midnight Crap",Season.WINTER, 150),
    SQUID("Squid",Season.WINTER, 80),
    TUNA("Tuna",Season.WINTER, 100),
    PERCH("Perch",Season.WINTER, 55),
    FLOUNDER("Flounder",Season.SPRING, 100),
    LIONFISH("Lionfish",Season.SPRING, 100),
    HERRING("Herring",Season.SPRING, 30),
    GHOSTFISH("Ghostfish",Season.SPRING, 45),
    TILAPIA("Tilapia",Season.SUMMER, 75),
    DORADO("Dorado",Season.SUMMER, 100),
    SUNFISH("Sunfish",Season.SUMMER, 30),
    RAINBOW_TROUT("Rainbow Trout",Season.SUMMER, 65),
    LEGEND("Legend",Season.SPRING, 5000),
    GLACIERFISH("Glacier Fish",Season.WINTER, 1000),
    ANGLER("Angler",Season.FALL, 900),
    CRIMSONFISH("Crimson Fish",Season.SUMMER, 1500);

    private final String name;
    private final Season season ;
    private final int price ;

    FishType(String name,Season season, int price) {
        this.name = name;
        this.season = season;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Season getSeason() {
        return season;
    }

    public int getPrice() {
        return price;
    }
}
