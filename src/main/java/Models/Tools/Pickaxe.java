package Models.Tools;

public class Pickaxe extends Tool {
    public Pickaxe(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    private final String name = "Pickaxe";

    @Override
    public String getName() {
        return name;
    }
}
