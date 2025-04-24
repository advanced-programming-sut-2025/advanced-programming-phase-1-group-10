package Models.Animal;

public enum AnimalType {
    CHICKEN("Coop", 800),
    DUCK("Coop", 1200),
    RABBIT("Coop", 8000),
    DINOSAUR("Coop", 14000),
    COW("Barn", 1500),
    GOAT("Barn", 4000),
    SHEEP("Barn", 8000),
    PIG("Barn", 16000);


    private final String enclosures ;
    private final int price ;

    AnimalType(String enclosures, int price) {
        this.enclosures = enclosures;
        this.price = price;
    }
}
