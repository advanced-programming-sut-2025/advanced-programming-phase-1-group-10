package Models.Tools;

public enum Quality {
    STARTER(0),
    BRONZE(1),
    SILVER(2),
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
