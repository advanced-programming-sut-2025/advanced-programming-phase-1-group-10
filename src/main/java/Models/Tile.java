package Models;

import Models.Animal.Animal;
import Models.Place.Place;
import Models.Planets.Seed;

public class Tile {

    private Person person;
    private Farm farm;
    private Place place;
    private Animal animal;

    private Position position;
    private TileType tileType;
    private Item item;

    private boolean plow;
    private boolean watered;

    private Seed plantedSeed;

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean getisPlow() {
        return plow;
    }

    public void setPlow(boolean plow) {
        this.plow = plow;
    }

    public boolean isWatered() {
        return watered;
    }

    public void setWatered(boolean watered) {
        this.watered = watered;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Position getPosition() {
        return position;
    }

    public void setPlantedSeed(Seed plantedSeed) {
        this.plantedSeed = plantedSeed;
    }

    public String getTile() {
        String ch;
        if (this.person != null) {
            ch = this.person.getSymbol();
        } else if (this.item != null) {
            ch = (item.getSymbol());
        } else if (this.getAnimal() != null) {
            ch = "*";
        } else if (this.getTileType() == TileType.Wall) {
            ch = "X";
        } else if (this.getisPlow()) {
            ch = "0";
        } else {
            ch = " ";
        }
        return getColoredText(ch);
    }

    public static final String RESET = "\u001B[0m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String BRIGHT_YELLOW_TEXT = "\u001B[93m"; // brighter than normal yellow


    public static final String BLACK_TEXT = "\u001B[30m";
    public static final String WHITE_TEXT = "\u001B[37m";

    public static final String BOLD = "\u001B[1m";

    public String getColoredText(String ch) {
        if (place == null) return WHITE_BACKGROUND + BLACK_TEXT + ch + RESET;

        String bgColor;
        String textColor = BLACK_TEXT;
        String style = "";

        switch (place.getSymbol()) {
            case "H":
                bgColor = RED_BACKGROUND;
                break;
            case "Q":
                bgColor = BLACK_BACKGROUND;
                textColor = WHITE_TEXT;
                break;
            case "C":
                bgColor = PURPLE_BACKGROUND;
                break;
            case " ":
                bgColor = WHITE_BACKGROUND;
                break;
            case "W":
                bgColor = BLUE_BACKGROUND;
                break;
            case "G":
                bgColor = GREEN_BACKGROUND;
                break;
            case "B":
                bgColor = YELLOW_BACKGROUND;
                break;
            case "~":
                bgColor = PURPLE_BACKGROUND;
                break;
            case "-":
                bgColor = YELLOW_BACKGROUND;
                break;
            default:
                bgColor = BLACK_BACKGROUND;
                textColor = WHITE_TEXT;
        }

        // Special styling for player "P"
        if (ch.equals("P")) {
            style = BOLD;
            textColor = BRIGHT_YELLOW_TEXT;
        }

        return bgColor + textColor + style + ch + RESET;
    }

}



