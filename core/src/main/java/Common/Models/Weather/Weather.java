package Common.Models.Weather;

public enum Weather {
    SUNNY("Sunny",1),
    RAIN("Rain", 1.5),
    STORM("Storm",1),
    SNOW("Snow",2),
    ;

    private final String name;
    private final double toolEnergyModifer;

    Weather(String name, double toolEnergyModifer) {
        this.name = name;
        this.toolEnergyModifer = toolEnergyModifer;
    }

    public String getName() {
        return name;
    }


    public double getToolEnergyModifer() {
        return toolEnergyModifer;
    }
}
