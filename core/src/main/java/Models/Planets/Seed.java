package Models.Planets;

import Assets.TreesAsset;
import Controllers.MessageSystem;
import Models.App;
import Models.Item;
import Models.Planets.Crop.CropTypeNormal;
import Models.PlayerStuff.Player;
import Models.Position;
import Models.Tile;
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
        return treesAsset.getCropSeedSprite(seedType.getName());
        // should add for other seeds
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

    public void use(Tile tile) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        if (seedType.getTreeCropType() != null) {
            if(!tile.getisPlow()){
                MessageSystem.showError("You sould plow this tile to plant a tree!",5.0f);
                return;
            }
            if (tile.getPlace() == null && tile.getItem() == null && tile.getCrop() == null && tile.getTree() == null) {
                String treeType = seedType.getTreeCropType().getName() + "_TREE" ;
                Tree newTree = new Tree(TreeType.valueOf(treeType.toUpperCase()));
                newTree.setPosition(new Position(tile.getPosition().getX(), tile.getPosition().getY()));
                tile.setTree(newTree);
                App.getInstance().getGameControllerFinal().getTreeController().addTree(newTree);
                MessageSystem.showInfo("The new tree planted successfully!",5.0f);
                this.numberOfSeed--;
                if (this.numberOfSeed <= 0) {
                    player.getInventory().getBackPack().removeItem(this);
                    player.getIventoryBarItems().remove(this);
                }
            }
        }
    }
}
