package Models.Tools;

public class Axe extends Tool {

    private final String name = "Axe";

    public Axe(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    @Override
    public String getName() {
        return name;
    }
}
