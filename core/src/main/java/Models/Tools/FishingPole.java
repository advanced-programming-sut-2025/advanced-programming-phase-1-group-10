package Models.Tools;

public class FishingPole extends Tool {

    public FishingPole(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    private final String name = "Fishing Pole";


    @Override
    public String getName() {
        return name;
    }
}
