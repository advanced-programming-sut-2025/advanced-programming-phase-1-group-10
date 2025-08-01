package Models.Place;

import Models.Animal.Animal;
import Models.Position;

import java.util.ArrayList;

public class Coop extends Place{
    private int animalCount = 0;
    private int capacity = 4;
    private boolean Big = false;
    private boolean Deluxe = false;
    private ArrayList<Animal> animals;

    public Coop(Position position, int height, int width) {
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
        return "CC";
    }

    @Override
    public String getPlaceName() {
        if(isDeluxe())
            return "Deluxe Coop";
        if(isBig())
            return "Big Coop";
        return "Coop";
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

    public boolean isFull(){
        if(isBig() && animalCount == 8){
            return true;
        } else if (isBig() && animalCount < 8){
            return false;
        }
        else if(isDeluxe() && animalCount == 12){
            return true;
        } else if(isDeluxe() && animalCount < 12){
            return false;
        }
        else return animalCount == 4;
    }
}
