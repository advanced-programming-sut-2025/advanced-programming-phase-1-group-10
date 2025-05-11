package Models.Tools;

public class Seythe extends Tool {
    public Seythe(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    private final String name = "Seythe";

    @Override
    public String getName() {
        return name;
    }
}
