package Models.Weather;

public enum Weather {
    SUNNY("Sunny"),
    RAIN("Rain"),
    STORM("Storm"),
    SNOW("Snow"),
    ;

    private final String name;

    Weather(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
