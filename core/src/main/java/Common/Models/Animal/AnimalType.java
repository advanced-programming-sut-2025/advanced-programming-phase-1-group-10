package Common.Models.Animal;

import Common.Models.Place.Maintenance;

public enum AnimalType {
    CHICKEN("Chicken",Maintenance.Coop, 800,1),
    DUCK("Duck",Maintenance.Coop, 1200,2),
    RABBIT("Rabbit",Maintenance.Coop, 8000,4),
    DINOSAUR("Dinosaur",Maintenance.Coop, 14000,7),
    COW("Cow",Maintenance.Barn, 1500,1),
    GOAT("Goat",Maintenance.Barn, 4000,2),
    SHEEP("Sheep",Maintenance.Barn, 8000,3),
    PIG("Pig",Maintenance.Barn, 16000,1);


    private final String type;
    private final Maintenance enclosures ;
    private final int price ;
    private int periode;

    AnimalType(String type, Maintenance enclosures, int price, int periode) {
        this.type = type;
        this.enclosures = enclosures;
        this.price = price;
        this.periode = periode;
    }

    public String getType() {
        return type;
    }

    public Maintenance getEnclosures() {
        return enclosures;
    }

    public int getPeriode() {
        return periode;
    }

    public int getPrice() {
        return price;
    }
}
