package src.Models.Animal;

import src.Models.Position;

import java.util.ArrayList;

public class Animal {

    private AnimalType animalType ;
    private ArrayList<AnimalProducts> animalProducts = new ArrayList<>();
    private String name ;
    private int friendShip;

    private Position position;

    public Animal(AnimalType animalType, String name) {
        this.animalType = animalType;
        this.name = name;
        this.friendShip = 0;
    }
}
