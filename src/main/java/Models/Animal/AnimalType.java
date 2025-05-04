package Models.Animal;

public enum AnimalType {
    CHICKEN("chicken","Coop", 800),
    DUCK("duck","Coop", 1200),
    RABBIT("rabbit","Coop", 8000),
    DINOSAUR("dinosaur","Coop", 14000),
    COW("cow","Barn", 1500),
    GOAT("goat","Barn", 4000),
    SHEEP("sheep","Barn", 8000),
    PIG("pig","Barn", 16000);


    private final String type;
    private final String enclosures ;
    private final int price ;

    AnimalType(String type, String enclosures, int price) {
        this.type = type;
        this.enclosures = enclosures;
        this.price = price;
    }

    public static AnimalType fromString(String type){
        for(AnimalType animalType : AnimalType.values()){
            if(animalType.type.equalsIgnoreCase(type)){
                return animalType;
            }
        }
        return null;
    }
}
