package Models.Tools;

public class TrashCan extends Tool {
    public TrashCan() {
        super();
    }
    private final String name = "Trash Can";

    @Override
    public String getName() {
        return name;
    }
}
