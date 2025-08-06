package Common.Models.Tools;

public enum Quality {
    STARTER(0),
    COPPER(1),
    STEEL(2),
    GOLD(3),
    IRIDIUM(4);

    private final int value;


    Quality(int bonus) {
        this.value = bonus;
    }

    public int getValue() {
        return value;
    }
}
