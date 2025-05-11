package Models.Tools;

public class Hoe extends Tool {

    public Hoe(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    private final String name = "Hoe";

    @Override
    public String getName() {
        return name;
    }
}
