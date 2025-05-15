package Models.Place;

import Models.Position;

public class Coop extends Place{
    private int animalCount = 0;
    private int capacity = 4;
    private boolean Big = false;
    private boolean Deluxe = false;

    public Coop(Position position, int height, int width) {
        super(position, height, width);
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
}
