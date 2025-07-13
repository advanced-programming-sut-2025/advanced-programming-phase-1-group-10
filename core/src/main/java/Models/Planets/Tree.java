package Models.Planets;

import Models.Item;

import java.util.ArrayList;

public class Tree implements Item {

    private TreeType treeType;
    private int numberOfTree = 1;
    private ArrayList<Fruit> fruits = new ArrayList<>();

    public Tree(TreeType treeType) {
        this.treeType = treeType;
    }

    @Override
    public String getName() {
        return treeType.getName();
    }

    @Override
    public String getSymbol() {
        return "Tr";
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
}
