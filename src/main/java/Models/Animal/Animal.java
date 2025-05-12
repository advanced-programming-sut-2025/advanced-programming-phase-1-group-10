package Models.Animal;

import Models.Position;

import java.util.ArrayList;

public class Animal {

    private AnimalType animalType ;
    private final ArrayList<AnimalProductType> animalProductTypes = new ArrayList<>();
    private String name ;
    private int friendShip;

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

    public void setFriendShip(int friendShip) {
        this.friendShip = friendShip;
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
