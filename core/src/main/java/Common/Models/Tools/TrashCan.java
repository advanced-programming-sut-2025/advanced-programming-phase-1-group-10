package Common.Models.Tools;

public class TrashCan extends Tool {
    public TrashCan(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    private final String name = "Trash Can";

    @Override
    public String getName() {
        return name;
    }
}
