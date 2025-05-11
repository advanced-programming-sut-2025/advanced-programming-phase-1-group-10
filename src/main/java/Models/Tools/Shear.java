package Models.Tools;

public class Shear extends Tool {
    public Shear(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    private final String name = "Shear";

    @Override
    public String getName() {
        return name;
    }
}
