package Models.Place;

import Models.Animal.Animal;
import Models.Position;

import java.util.ArrayList;

public class Barn extends Place{
    private int animalCount = 0;
    private int capacity = 4 ;
    private boolean Big = false;
    private boolean Deluxe = false;
    private ArrayList<Animal> animals;

    public Barn(Position position, int height, int width) {
        super(position, height, width);
        this.animals = new ArrayList<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isBig() {
        return Big;
    }

    public void setBig() {
        Big = true;
        this.capacity = 8;
    }

    public boolean isDeluxe() {
        return Deluxe;
    }

    public void setDeluxe() {
        Deluxe = true;
        this.capacity = 12;
    }

    public int getAnimalCount() {
        return animalCount;
    }

    public void setAnimalCount(int animalCount) {
        this.animalCount = animalCount;
    }

    @Override
    public String getSymbol() {
        return "BB";
    }

    @Override
    public String getPlaceName() {
        if(isDeluxe())
            return "Deluxe Barn";
        if(isBig())
            return "Big Barn";
        return "Barn";
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void addAnimal(Animal animal){
        animals.add(animal);
    }

    public void setBig(boolean big) {
        Big = big;
    }

    public void setDeluxe(boolean deluxe) {
        Deluxe = deluxe;
    }
}
