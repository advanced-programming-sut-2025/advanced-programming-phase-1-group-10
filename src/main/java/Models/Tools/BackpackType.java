package Models.Tools;

public enum BackpackType {
    STARTER(12),
    LARGE(24),
    DELUXE(100),
    ;

    final int capacity;

    BackpackType(int capacity) {
        this.capacity = capacity;
    }
}