package Models.Tools;

public class WateringCan extends Tool {
    public WateringCan(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }


    private final String name = "Watering Can";

    @Override
    public String getName() {
        return name;
    }
}
