package Models.Animal;

import Models.Place.Maintenance;

public enum AnimalType {
    CHICKEN("chicken",Maintenance.Coop, 800),
    DUCK("duck",Maintenance.Coop, 1200),
    RABBIT("rabbit",Maintenance.Coop, 8000),
    DINOSAUR("dinosaur",Maintenance.Coop, 14000),
    COW("cow",Maintenance.Barn, 1500),
    GOAT("goat",Maintenance.Barn, 4000),
    SHEEP("sheep",Maintenance.Barn, 8000),
    PIG("pig",Maintenance.Barn, 16000);


    private final String type;
    private final Maintenance enclosures ;
    private final int price ;

    AnimalType(String type, Maintenance enclosures, int price) {
        this.type = type;
        this.enclosures = enclosures;
        this.price = price;
    }
}
