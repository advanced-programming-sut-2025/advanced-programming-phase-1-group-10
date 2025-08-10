package Common.Models.Tools;

public enum BackpackType {

    TRADE_USAGE(5),


    STARTER(12),
    LARGE(24),
    DELUXE(Integer.MAX_VALUE),
    ;

    final int capacity;

    BackpackType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
