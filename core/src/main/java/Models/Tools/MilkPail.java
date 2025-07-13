package Models.Tools;

public class MilkPail extends Tool {

    public MilkPail(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    private final String name = "Milk Pail";

    @Override
    public String getName() {
        return name;
    }
}
