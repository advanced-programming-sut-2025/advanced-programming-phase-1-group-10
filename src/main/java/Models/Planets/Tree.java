package Models.Planets;

import Models.Item;

public class Tree implements Item {

    private TreeType treeType;
    private int numberOfTree = 1;

    public Tree(TreeType treeType) {
        treeType = treeType;
    }

    @Override
    public String getName() {
        return treeType.getName();
    }

    @Override
    public String getSymbol() {
        return "T";
    }


    @Override
    public int getNumber() {
        return numberOfTree;
    }

    @Override
    public void setNumber(int number) {
        numberOfTree = number;
    }
}
