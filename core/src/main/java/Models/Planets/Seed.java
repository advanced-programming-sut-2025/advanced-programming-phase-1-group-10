package Models.Planets;

import Assets.TreesAsset;
import Models.Item;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Seed implements Item {
    private SeedType seedType;
    private int numberOfSeed;
    private TreesAsset treesAsset;

    public Seed(SeedType seedType, int numberOfSeed) {
        this.seedType = seedType;
        this.numberOfSeed = numberOfSeed;
        this.treesAsset = new TreesAsset();
    }

    @Override
    public String getName() {
        return seedType.getName();
    }

    @Override
    public Sprite show() {
        for(TreeCropType treeCropType : TreeCropType.values()){
            if(treeCropType.getSource().equals(seedType.getName())){
                return treesAsset.getSaplingSprite(treeCropType.getName());
            }
        }
        // should add for other seeds
        return null;
    }

    @Override
    public int getNumber() {
        return numberOfSeed;
    }

    @Override
    public void setNumber(int number){
        numberOfSeed = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Seed(seedType, number);
    }
}
