package Models.Planets;

import Assets.TreesAsset;
import Models.App;
import Models.DateTime.DateTime;
import Models.DateTime.Season;
import Models.Item;
import Models.Position;
import Models.Tile;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Tree implements Item {

    private TreeType treeType;
    private int numberOfTree = 1;
    private ArrayList<Fruit> fruits = new ArrayList<>();
    private DateTime plantedDate;
    private int growthStage ;
    private int daysSinceLastFruit = 0;
    private TreesAsset treesAsset;
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Tree(TreeType treeType) {
        this.treeType = treeType;
        this.plantedDate = App.getInstance().getCurrentGame().getGameTime().copy();
        this.treesAsset = new TreesAsset();
        this.growthStage = 1;
    }

    @Override
    public String getName() {
        return treeType.getName();
    }

    @Override
    public Sprite show() {
        return treesAsset.getSaplingSprite(treeType.getName());
    }


    public void updateGrowth() {
        int daysPassed = DateTime.daysDifference(plantedDate, App.getInstance().getCurrentGame().getGameTime());
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
            }
        }
    }



    public Sprite getSprite(Season currentSeason) {
        if (growthStage == 0) {
            return treesAsset.getSaplingSprite(treeType.getName());
        } else if (growthStage >= 1 && growthStage <= 4) {
            return new Sprite(treesAsset.getStageTexture(treeType.getName(), growthStage));
        } else if (growthStage == 5) {
            if (treesAsset.getStage5FruitTexture(treeType.getName()) != null) {
                return new Sprite(treesAsset.getStage5FruitTexture(treeType.getName()));
            } else {
                return new Sprite(treesAsset.getStage5SeasonalTexture(treeType.getName(), currentSeason));
            }
        }
        return null;
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
        if (growthStage < 4) {
            growthStage++;
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
}
