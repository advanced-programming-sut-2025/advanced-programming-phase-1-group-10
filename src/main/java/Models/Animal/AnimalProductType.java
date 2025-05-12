package Models.Animal;

public enum AnimalProductType {
    EGG("Egg", 50),
    LARGE_EGG("Large Egg", 95),
    DUCK_EGG("Duck Egg", 95),
    DUCK_FEATHER("Duck Feather", 250),
    RABBIT_WOOL("Rabbit Wool", 340),
    RABBIT_LEG("Rabbit Leg", 565),
    DINOSAUR_EGG("Dinosaur Egg", 350),
    COW_MILK("Cow Milk", 125),
    COW_LARGE_MILK("Cow Large Milk", 190),
    GOAT_MILK("Goat Milk", 225),
    GOAT_LARGE_MILK("Goat Large Milk", 345),
    SHEEP_WOOL("Sheep Wool", 340),
    TRUFFLE("Truffle", 625);

    private final String name;
    private final int price;

    AnimalProductType(String name , int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
}
