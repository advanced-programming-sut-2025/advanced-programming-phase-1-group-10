package Models.Planets;

import Models.App;
import Models.DateTime.DateTime;
import Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Tree implements Item {

    private TreeType treeType;
    private int numberOfTree = 1;
    private ArrayList<Fruit> fruits = new ArrayList<>();
    private DateTime plantedDate;
    private int growthStage = 0;
    private int daysSinceLastFruit = 0;

    public Tree(TreeType treeType) {
        this.treeType = treeType;
        this.plantedDate = App.getInstance().getCurrentGame().getGameTime();

    }

    @Override
    public String getName() {
        return treeType.getName();
    }

    @Override
    public Sprite show() {
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
