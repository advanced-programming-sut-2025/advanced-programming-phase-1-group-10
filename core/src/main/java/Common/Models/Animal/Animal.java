package Common.Models.Animal;

import Common.Models.App;
import Common.Models.DateTime.DateTime;
import Common.Models.Place.Barn;
import Common.Models.Place.Coop;
import Common.Models.Position;
import Common.Models.Tile;

import java.util.ArrayList;
import java.util.List;

import static Client.Controllers.GameController.getTileByPosition;

public class Animal {


    private AnimalType animalType;
    private final ArrayList<AnimalProductType> animalProductTypes = new ArrayList<>();
    private String name;
    private int friendShip;
    private List<AnimalProduct> producedProducts;
    private Position position;
    private boolean petted = false;
    private boolean fed = false;


    public static final float ANIMAL_WIDTH = 64f;
    public static final float ANIMAL_HEIGHT = 64f;
    private float x;
    private float y;
    private float speed = 80f;
    private float stateTime = 0f;
    private Direction direction = Direction.DOWN;
    private boolean moving = false;
    private boolean free = false;
    private Position homePosition;
    private float lastMoveTime = 0f;


    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Animal(AnimalType animalType, String name) {
        this.animalType = animalType;
        this.name = name;
        this.friendShip = 0;
        this.producedProducts = new ArrayList<>();
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

    public void feed() {
        fed = true;
        friendShip += 8;
    }

    public ProductQuality calculateQuality() {
        double random = Math.random();
        double quality = ((double) friendShip / 1000) * (0.5 + 0.5 * random);

        if (quality <= 0.5) {
            return ProductQuality.NORMAL;
        } else if (quality <= 0.7) {
            return ProductQuality.SILVER;
        } else if (quality <= 0.9) {
            return ProductQuality.GOLD;
        } else {
            return ProductQuality.IRIDIUM;
        }
    }

    // this methode should be fixed
    public void produce() {
        AnimalProduct newProduct;
        if (friendShip > 10 && animalProductTypes.size() > 1) {
            double random = 0.5 + Math.random();
            double probability = (friendShip + 150 * random) / 1500;
            if (probability > 0.05) {
                newProduct = new AnimalProduct(animalProductTypes.get(1), calculateQuality(), 1);
            } else {
                newProduct = new AnimalProduct(animalProductTypes.get(0), calculateQuality(), 1);
            }
        } else {
            newProduct = new AnimalProduct(animalProductTypes.get(0), calculateQuality(), 1);
        }
        producedProducts.add(newProduct);
        System.out.println("new product : " + newProduct.getName());
    }

    public List<AnimalProduct> getProducedProducts() {
        return producedProducts;
    }

    public void removeProduct(AnimalProduct product) {
        producedProducts.remove(product);
    }

    public void setFriendShip(int friendShip) {
        this.friendShip = friendShip;
    }

    public int getPrice() {
        return (int) (animalType.getPrice() * (((double) friendShip / 1000) + 0.3));
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isFed() {
        return fed;
    }

    public static Animal animalFactory(String animalType, String name) {
        for (AnimalType at : AnimalType.values()) {
            if (at.name().equalsIgnoreCase(animalType)) {
                return new Animal(at, name);
            }
        }
        return null;
    }

    // this method should be complete
    public static void updateAnimalState(Animal animal) {
        DateTime dateAndTime = App.getInstance().getCurrentGame().getGameTime();
//        if (!animal.isFed()) {
//            animal.setFriendShip(animal.getFriendShip() - 20);
//        }
//        if (!animal.isPetted()) {
//            animal.setFriendShip(animal.getFriendShip() + (animal.getFriendShip() / 200) - 10);
//        }
//        if (!isCorrectEnclosure(animal)) {
//            animal.setFriendShip(animal.getFriendShip() - 20);
//        }

        if (true) {
            animal.produce();
        }
//        animal.petted = false;
//        animal.fed = false;
    }

    private static boolean isCorrectEnclosure(Animal animal) {
        Tile tile = getTileByPosition(animal.getPosition());
        if (tile == null) return false;

        if (animal.getAnimalType().getEnclosures().toString().equalsIgnoreCase("COOP"))
            return tile.getPlace() instanceof Coop;
        else if (animal.getAnimalType().getEnclosures().toString().equalsIgnoreCase("BARN"))
            return tile.getPlace() instanceof Barn;

        return false;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Position getHomePosition() {
        return homePosition;
    }

    public void setHomePosition(Position homePosition) {
        this.homePosition = homePosition;
    }

    public float getLastMoveTime() {
        return lastMoveTime;
    }

    public void setLastMoveTime(float lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }
}
