package Models.Animal;

import Models.Position;

import java.util.ArrayList;

public class Animal {

    private AnimalType animalType ;
    private final ArrayList<AnimalProductType> animalProductTypes = new ArrayList<>();
    private String name ;
    private int friendShip;
    private AnimalProduct currentProduct;

    private Position position;

    private boolean petted = false;
    private boolean fed = false;

    public Animal(AnimalType animalType, String name) {
        this.animalType = animalType;
        this.name = name;
        this.friendShip = 0;
        switch (animalType) {
            case CHICKEN -> {
                this.animalProductTypes.add(AnimalProductType.EGG);
                this.animalProductTypes.add(AnimalProductType.LARGE_EGG);
            }
            case DUCK -> {
                this.animalProductTypes.add(AnimalProductType.DUCK_EGG);
                this.animalProductTypes.add(AnimalProductType.DUCK_FEATHER);
            }
            case RABBIT -> {
                this.animalProductTypes.add(AnimalProductType.RABBIT_WOOL);
                this.animalProductTypes.add(AnimalProductType.RABBIT_LEG);
            }
            case DINOSAUR -> this.animalProductTypes.add(AnimalProductType.DINOSAUR_EGG);
            case COW -> {
                this.animalProductTypes.add(AnimalProductType.COW_MILK);
                this.animalProductTypes.add(AnimalProductType.COW_LARGE_MILK);
            }
            case GOAT -> {
                this.animalProductTypes.add(AnimalProductType.GOAT_MILK);
                this.animalProductTypes.add(AnimalProductType.GOAT_LARGE_MILK);
            }
            case SHEEP -> this.animalProductTypes.add(AnimalProductType.SHEEP_WOOL);
            case PIG -> this.animalProductTypes.add(AnimalProductType.TRUFFLE);
        }
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public ArrayList<AnimalProductType> getAnimalProductTypes() {
        return animalProductTypes;
    }

    public String getName() {
        return name;
    }

    public int getFriendShip() {
        return friendShip;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isPetted() {
        return petted;
    }

    public void pet() {
        petted = true;
        friendShip += 15;
    }

    public void feed(){
        fed = true;
        friendShip += 8;
    }

    public ProductQuality calculateQuality() {
        double random = Math.random();
        double quality = ((double) friendShip /1000) * (0.5 + 0.5 * random);

        if(quality <= 0.5) {
            return ProductQuality.NORMAL;
        }
        else if(quality <= 0.7) {
            return ProductQuality.SILVER;
        }
        else if(quality <= 0.9) {
            return ProductQuality.GOLD;
        }
        else {
            return ProductQuality.IRIDIUM;
        }
    }

    public void produce() {
        if(friendShip > 100 && animalProductTypes.size() > 1) {
            double random = 0.5 + Math.random();
            double probability = (friendShip + 150 * random) / 1500;

            if(probability > 0.5) {
                currentProduct = new AnimalProduct(animalProductTypes.get(1), calculateQuality());
            }
            else {
                currentProduct = new AnimalProduct(animalProductTypes.get(0), calculateQuality());
            }
        }
        else {
            currentProduct = new AnimalProduct(animalProductTypes.get(0), calculateQuality());
        }
    }

    public AnimalProduct getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(AnimalProduct currentProduct) {
        this.currentProduct = currentProduct;
    }

    public void setFriendShip(int friendShip) {
        this.friendShip = friendShip;
    }

    public int getPrice() {
        return (int) (animalType.getPrice() * (((double) friendShip /1000) + 0.3));
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isFed() {
        return fed;
    }

    public static Animal animalFactory(String animalType, String name) {
        for(AnimalType at: AnimalType.values()) {
            if(at.name().equalsIgnoreCase(animalType)) {
                return new Animal(at, name);
            }
        }
        return null;
    }
}
