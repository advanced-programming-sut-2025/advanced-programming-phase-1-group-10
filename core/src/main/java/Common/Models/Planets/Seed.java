package Common.Models.Planets;

import Client.Assets.CropAsset;
import Client.Assets.TreesAsset;
import Client.Controllers.MessageSystem;
import Common.Models.App;
import Common.Models.DateTime.Season;
import Common.Models.Item;
import Common.Models.Planets.Crop.Crop;
import Common.Models.Planets.Crop.CropTypeNormal;
import Common.Models.PlayerStuff.Player;
import Common.Models.Position;
import Common.Models.Tile;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Seed implements Item {
    private SeedType seedType;
    private int numberOfSeed;
    private TreesAsset treesAsset;
    private boolean mixed;

    public Seed(SeedType seedType, int numberOfSeed) {
        this.seedType = seedType;
        this.numberOfSeed = numberOfSeed;
        this.treesAsset = new TreesAsset();
        this.mixed = false;
    }

    @Override
    public String getName() {
        return seedType.getName();
    }

    @Override
    public Sprite show() {
        if(mixed)
            return CropAsset.mixedSeedSprite;
        for(TreeCropType treeCropType : TreeCropType.values()){
            if(treeCropType.getSource().equals(seedType.getName())){
                return treesAsset.getSaplingSprite(treeCropType.getName());
            }
        }
        return treesAsset.getCropSeedSprite(seedType.getName());

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

    public void setMixed(boolean mixed) {
        this.mixed = mixed;
    }

    public void use(Tile tile) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();

        if (!tile.getisPlow()) {
            MessageSystem.showError("You should plow this tile to plant!", 5.0f);
            return;
        }

        if (tile.getPlace() != null || tile.getItem() != null || tile.getCrop() != null || tile.getTree() != null) {
            MessageSystem.showError("This tile is not empty!", 5.0f);
            return;
        }

        if (seedType.getTreeCropType() != null) {
            String treeType = seedType.getTreeCropType().getName() + "_TREE";
            try {
                Tree newTree = new Tree(TreeType.valueOf(treeType.toUpperCase()));
                newTree.setPosition(new Position(tile.getPosition().getX(), tile.getPosition().getY()));
                tile.setTree(newTree);
                tile.setItem(newTree);
                App.getInstance().getGameControllerFinal().getTreeController().addTree(newTree);
                MessageSystem.showInfo("The new tree planted successfully!", 5.0f);
            } catch (IllegalArgumentException e) {
                MessageSystem.showError("Invalid tree type: " + treeType, 5.0f);
                return;
            }
        }
        else {
            CropTypeNormal cropType = null;

            for (CropTypeNormal type : CropTypeNormal.values()) {
                if (type.getSource() == seedType) {
                    cropType = type;
                    break;
                }
            }

            if (cropType != null) {
                if (!cropType.getSeasons().contains(App.getInstance().getCurrentGame().getGameTime().getSeason())) {
                    MessageSystem.showError("You can't plant " + cropType.getName() + " in this season!", 5.0f);
                    return;
                }

                Crop newCrop = new Crop(cropType, 1);
                newCrop.setWhenPlanted(App.getInstance().getCurrentGame().getGameTime().copy());
                tile.setCrop(newCrop);
                tile.setItem(newCrop);
                MessageSystem.showInfo("The new crop planted successfully!", 5.0f);
            } else if(mixed){
                Season season = App.getInstance().getCurrentGame().getGameTime().getSeason();
                List<CropTypeNormal> crops = new ArrayList<>();
                for(CropTypeNormal cropTypeNormal : CropTypeNormal.values()){
                    for(Season season1 : cropTypeNormal.getSeasons()){
                        if(season1.equals(season))
                            crops.add(cropTypeNormal);
                    }
                }
                int index = new Random().nextInt(0,crops.size() - 1);
                cropType = crops.get(index);
                Crop newCrop = new Crop(cropType,1);
                newCrop.setWhenPlanted(App.getInstance().getCurrentGame().getGameTime().copy());
                tile.setCrop(newCrop);
                tile.setItem(newCrop);
                MessageSystem.showInfo("The new crop planted successfully!", 5.0f);
            }
            else if(cropType == null) {
                MessageSystem.showError("Invalid seed type!", 5.0f);
                return;
            }
        }

        this.numberOfSeed--;
        if (this.numberOfSeed <= 0) {
            player.getInventory().getBackPack().removeItem(this);
            player.getIventoryBarItems().remove(this);
        }
    }
}
