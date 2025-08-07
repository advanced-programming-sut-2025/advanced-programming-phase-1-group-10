package Common.Models.Tools;

public class TrashCan extends Tool {
    public TrashCan(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }


    @Override
    public String getName() {
        return "Trash Can";
    }
}
