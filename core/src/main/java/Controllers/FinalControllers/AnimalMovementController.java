package Controllers.FinalControllers;

import Assets.AnimalAsset;
import Models.Animal.Animal;
import Models.App;
import Models.Map;
import Models.PlayerStuff.Player;
import Models.Position;
import Models.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class AnimalMovementController {
    private static final float DIRECTION_CHANGE_INTERVAL = 2.0f;

    private static final float MAX_DISTANCE = 5 * Map.tileSize;

    private final AnimalAsset animalAsset;
    private final Map gameMap;
    private final List<Animal> freeAnimals = new ArrayList<>();

    public AnimalMovementController(AnimalAsset animalAsset, Map gameMap) {
        this.animalAsset = animalAsset;
        this.gameMap = gameMap;
    }

    public void update(float delta) {
        for (Animal animal : freeAnimals) {
            if (animal.isFree()) {
                animal.setStateTime(animal.getStateTime() + delta);


                if (animal.getStateTime() - animal.getLastMoveTime() >= DIRECTION_CHANGE_INTERVAL) {
                    chooseRandomDirection(animal);
                    animal.setLastMoveTime(animal.getStateTime());
                }

                if (animal.isMoving()) {
                    updateAnimalPosition(animal, delta);
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Animal animal : freeAnimals) {
            if (animal.isFree()) {
                renderAnimal(animal, batch);
            }
        }
    }

    private void renderAnimal(Animal animal, SpriteBatch batch) {
        TextureRegion currentFrame;
        String animalType = animal.getAnimalType().getType();

        if (animal.isMoving()) {
            float stateTime = animal.getStateTime();
            switch (animal.getDirection()) {
                case UP:
                    currentFrame = animalAsset.getWalkUpAnimation(animalType).getKeyFrame(stateTime, true);
                    break;
                case DOWN:
                    currentFrame = animalAsset.getWalkDownAnimation(animalType).getKeyFrame(stateTime, true);
                    break;
                case LEFT:
                    currentFrame = animalAsset.getWalkLeftAnimation(animalType).getKeyFrame(stateTime, true);
                    break;
                case RIGHT:
                    currentFrame = animalAsset.getWalkRightAnimation(animalType).getKeyFrame(stateTime, true);
                    break;
                default:
                    currentFrame = animalAsset.getIdleFrame(animalType);
                    break;
            }
        } else {
            currentFrame = animalAsset.getIdleFrame(animalType);
        }

        if (currentFrame != null) {
            batch.draw(
                currentFrame,
                animal.getX(),
                animal.getY(),
                Animal.ANIMAL_WIDTH,
                Animal.ANIMAL_HEIGHT
            );
        }
    }

    private void chooseRandomDirection(Animal animal) {
        int direction = MathUtils.random(0, 4);
        Animal.Direction newDirection;

        switch (direction) {
            case 0: newDirection = Animal.Direction.UP; break;
            case 1: newDirection = Animal.Direction.DOWN; break;
            case 2: newDirection = Animal.Direction.LEFT; break;
            case 3: newDirection = Animal.Direction.RIGHT; break;
            default:

                animal.setMoving(false);
                return;
        }

        animal.setDirection(newDirection);
        animal.setMoving(true);
    }

    private void updateAnimalPosition(Animal animal, float delta) {
        float currentX = animal.getX();
        float currentY = animal.getY();
        float nextX = currentX;
        float nextY = currentY;

        float moveAmount = animal.getSpeed() * delta;

        switch (animal.getDirection()) {
            case UP: nextY += moveAmount; break;
            case DOWN: nextY -= moveAmount; break;
            case LEFT: nextX -= moveAmount; break;
            case RIGHT: nextX += moveAmount; break;
        }


        Position homePos = animal.getHomePosition();
        float homeX = homePos.getX() * Map.tileSize;
        float homeY = homePos.getY() * Map.tileSize;
        float distanceFromHome = (float) Math.sqrt(Math.pow(nextX - homeX, 2) + Math.pow(nextY - homeY, 2));

        if (distanceFromHome <= MAX_DISTANCE && canMoveTo(nextX, nextY, gameMap)) {
            animal.setX(nextX);
            animal.setY(nextY);
        } else {

            animal.setMoving(false);
            chooseRandomDirection(animal);
        }
    }

    private boolean canMoveTo(float nextX, float nextY, Map map) {
        float width = Animal.ANIMAL_WIDTH;
        float height = Animal.ANIMAL_HEIGHT;

        int[] rows = {
            (int) (nextY / Map.tileSize),
            (int) ((nextY + height - 1) / Map.tileSize)
        };
        int[] cols = {
            (int) (nextX / Map.tileSize),
            (int) ((nextX + width - 1) / Map.tileSize)
        };

        for (int row : rows) {
            for (int col : cols) {
                if (row < 0 || row >= Map.mapHeight || col < 0 || col >= Map.mapWidth) {
                    return false;
                }
                Tile tile = map.getMap()[row][col];
                if (tile == null || !tile.isWalkable()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void releaseAnimal(Animal animal, float x, float y) {
        if (!freeAnimals.contains(animal)) {
            freeAnimals.add(animal);
        }

        animal.setFree(true);
        animal.setX(x);
        animal.setY(y);
        animal.setHomePosition(new Position((int)(x / Map.tileSize), (int)(y / Map.tileSize)));
        animal.setStateTime(0);
        animal.setLastMoveTime(0);
        animal.setMoving(false);
        animal.setDirection(Animal.Direction.DOWN);

        System.out.println("Animal " + animal.getName() + " released at: " + x + ", " + y);
    }

    public void returnAnimalToBuilding(Animal animal) {
        animal.setFree(false);
        freeAnimals.remove(animal);
    }

    public List<Animal> getFreeAnimals() {
        return freeAnimals;
    }
}
