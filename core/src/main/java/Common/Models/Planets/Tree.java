package Common.Models.Planets;

import Client.Assets.TreesAsset;
import Common.Models.App;
import Common.Models.DateTime.DateTime;
import Common.Models.DateTime.Season;
import Common.Models.Item;
import Common.Models.Map;
import Common.Models.Position;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class Tree implements Item {

    private TreeType treeType;
    private int numberOfTree = 1;
    private ArrayList<Fruit> fruits = new ArrayList<>();
    private DateTime plantedDate;
    private int growthStage;
    private int daysSinceLastFruit = 0;
    private final TreesAsset treesAsset = new TreesAsset();
    private Position position;
    private boolean hasFruits = false;
    private boolean choped;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Tree(TreeType treeType) {
        this.treeType = treeType;
        this.plantedDate = App.getInstance().getCurrentGame().getGameTime().copy();
        this.growthStage = 1;
        this.choped = false;
    }

    @Override
    public String getName() {
        return treeType.getName();
    }

    @Override
    public Sprite show() {
        if (growthStage == 0) {
            return treesAsset.getSaplingSprite(treeType.getName());
        } else if (growthStage >= 1 && growthStage <= 4) {
            return new Sprite(treesAsset.getStageTexture(treeType.getName(), growthStage));
        } else if (growthStage == 5) {
            if (hasFruits && treesAsset.getStage5FruitTexture(treeType.getName()) != null) {
                return new Sprite(treesAsset.getStage5FruitTexture(treeType.getName()));
            } else {
                return new Sprite(treesAsset.getStage5SeasonalTexture(treeType.getName(), App.getInstance().getCurrentGame().getGameTime().getSeason()));
            }
        }
        return null;
    }

    public void updateGrowth() {
        int daysPassed = calculateDaysDifference(plantedDate, App.getInstance().getCurrentGame().getGameTime());

        if (growthStage < 5) {
            int newGrowthStage = 1;
            int totalGrowthTime = 0;

            for (int i = 0; i < treeType.getItems().size() && newGrowthStage < 5; i++) {
                int stageTime = treeType.getItems().get(i);

                totalGrowthTime += stageTime;

                if (daysPassed >= totalGrowthTime) {
                    newGrowthStage++;
                } else {
                    break;
                }
            }

            newGrowthStage = Math.min(5, newGrowthStage);
            if (newGrowthStage > growthStage) {
                growthStage = newGrowthStage;

                if (growthStage == 5) {
                    produceFruit();
                }
            }
        } else if (growthStage == 5) {

            updateFruitProduction();
        }
    }

    private void updateFruitProduction() {
        if (!hasFruits) {
            daysSinceLastFruit++;

            TreeCropType treeCropType = null;
            for (TreeCropType type : TreeCropType.values()) {
                if (type.getName().equalsIgnoreCase(treeType.getName())) {
                    treeCropType = type;
                    break;
                }
            }

            if (treeCropType != null) {
                int fruitCycle = treeCropType.getFruitHarvestCycle();

                if (daysSinceLastFruit >= fruitCycle) {
                    produceFruit();
                }
            }
        }
    }

    private void produceFruit() {
        TreeCropType treeCropType = null;
        for (TreeCropType type : TreeCropType.values()) {
            if (type.getName().equalsIgnoreCase(treeType.getName())) {
                treeCropType = type;
                break;
            }
        }

        if (treeCropType != null && treeCropType.getFruitType() != null) {

            Fruit fruit = new Fruit(treeCropType.getFruitType(), new Random().nextInt(2,3));
            fruits.add(fruit);
            hasFruits = true;
        }
    }

        public Fruit harvestFruit() {
        if (!fruits.isEmpty() && hasFruits) {
            Fruit fruit = fruits.remove(0);

            if (fruits.isEmpty()) {
                hasFruits = false;
                daysSinceLastFruit = 0;
            }
            return fruit;
        }

        return null;
    }

    public Sprite getSprite(Season currentSeason) {
        if (growthStage == 0) {
            return treesAsset.getSaplingSprite(treeType.getName());
        } else if (growthStage >= 1 && growthStage <= 4) {
            return new Sprite(treesAsset.getStageTexture(treeType.getName(), growthStage));
        } else if (growthStage == 5) {
            if (hasFruits && treesAsset.getStage5FruitTexture(treeType.getName()) != null) {
                return new Sprite(treesAsset.getStage5FruitTexture(treeType.getName()));
            } else {
                return new Sprite(treesAsset.getStage5SeasonalTexture(treeType.getName(), currentSeason));
            }
        }
        return null;
    }

        public void renderAt(SpriteBatch batch, int tileY, int tileX) {
        Season currentSeason = App.getInstance().getCurrentGame().getGameTime().getSeason();
        Sprite treeSprite = getSprite(currentSeason);

        if (treeSprite != null) {
            float x = tileX * Map.tileSize;
            float y = tileY * Map.tileSize;
            batch.draw(treeSprite, x, y, treeSprite.getWidth(), treeSprite.getHeight());
        }
    }

    @Override
    public int getNumber() {
        return numberOfTree;
    }

    @Override
    public void setNumber(int number) {
        numberOfTree = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Tree(treeType);
    }

    public TreeType getTreeType() {
        return treeType;
    }

    public ArrayList<Fruit> getFruits() {
        return fruits;
    }

    public void setFruits(ArrayList<Fruit> fruits) {
        this.fruits = fruits;
        this.hasFruits = !fruits.isEmpty();
    }

    public DateTime getPlantedDate() {
        return plantedDate;
    }

    public void setPlantedDate(DateTime plantedDate) {
        this.plantedDate = plantedDate;
    }

    public Integer getGrowthStage() {
        return growthStage;
    }

    public void setGrowthStage(int growthStage) {
        this.growthStage = growthStage;
    }

    public void incrementGrowthStage() {
        if (growthStage < 5) {
            growthStage++;

            if (growthStage == 5) {
                produceFruit();
            }
        }
    }

    public int getDaysSinceLastFruit() {
        return daysSinceLastFruit;
    }

    public void incrementDaysSinceLastFruit() {
        daysSinceLastFruit++;
    }

    public void resetDaysSinceLastFruit() {
        daysSinceLastFruit = 0;
    }

    public void setChoped(boolean choped) {
        this.choped = choped;
    }

    public boolean isChoped() {
        return choped;
    }

    public boolean hasFruits() {
        return hasFruits;
    }

    private int calculateDaysDifference(DateTime startDate, DateTime endDate) {
        int startTotalDays = startDate.getYear() * 12 * 28 + (startDate.getMonth() - 1) * 28 + startDate.getDay();
        int endTotalDays = endDate.getYear() * 12 * 28 + (endDate.getMonth() - 1) * 28 + endDate.getDay();
        return endTotalDays - startTotalDays;
    }
}
