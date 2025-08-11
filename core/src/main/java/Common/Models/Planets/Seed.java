package Common.Models.Planets;

import Client.Assets.CropAsset;
import Client.Assets.TreesAsset;
import Client.Controllers.MessageSystem;
import Client.Network.ClientNetworkManager;
import Common.Models.App;
import Common.Models.DateTime.Season;
import Common.Models.Item;
import Common.Models.Planets.Crop.Crop;
import Common.Models.Planets.Crop.CropTypeNormal;
import Common.Models.PlayerStuff.Player;
import Common.Models.Position;
import Common.Models.Tile;
import Common.Network.Messages.MessageTypes.PlantSeedMessage;
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

        boolean isTreePlanted = false;
        String plantedItemName = null;

        if (seedType.getTreeCropType() != null) {
            String treeTypeString = seedType.getTreeCropType().getName() + "_TREE";
            try {
                Tree newTree = new Tree(TreeType.valueOf(treeTypeString.toUpperCase()));
                newTree.setPosition(new Position(tile.getPosition().getX(), tile.getPosition().getY()));
                tile.setTree(newTree);
                tile.setItem(newTree);
                App.getInstance().getGameControllerFinal().getTreeController().addTree(newTree);
                MessageSystem.showInfo("The new tree planted successfully!", 5.0f);
                isTreePlanted = true;
                plantedItemName = newTree.getName();
            } catch (IllegalArgumentException e) {
                MessageSystem.showError("Invalid tree type: " + treeTypeString, 5.0f);
                return;
            }
        }
        else {
            CropTypeNormal selectedCropType = null;

            if (mixed) {
                Season currentSeason = App.getInstance().getCurrentGame().getGameTime().getSeason();
                List<CropTypeNormal> suitableCrops = new ArrayList<>();
                for(CropTypeNormal ctn : CropTypeNormal.values()){
                    if(ctn.getSeasons().contains(currentSeason))
                        suitableCrops.add(ctn);
                }
                if (suitableCrops.isEmpty()) {
                    MessageSystem.showError("No suitable mixed crop for this season!", 5.0f);
                    return;
                }
                selectedCropType = suitableCrops.get(new Random().nextInt(suitableCrops.size()));
            } else {
                for (CropTypeNormal ctn : CropTypeNormal.values()) {
                    if (ctn.getSource() == seedType) {
                        selectedCropType = ctn;
                        break;
                    }
                }
            }

            if (selectedCropType != null) {
                if (!selectedCropType.getSeasons().contains(App.getInstance().getCurrentGame().getGameTime().getSeason())) {
                    MessageSystem.showError("You can't plant " + selectedCropType.getName() + " in this season!", 5.0f);
                    return;
                }

                Crop newCrop = new Crop(selectedCropType, 1);
                newCrop.setWhenPlanted(App.getInstance().getCurrentGame().getGameTime().copy()); // زمان کاشت
                tile.setCrop(newCrop);
                tile.setItem(newCrop);
                MessageSystem.showInfo("The new crop planted successfully!", 5.0f);
                plantedItemName = newCrop.getName();
            } else {
                MessageSystem.showError("Invalid seed type or no crop found!", 5.0f);
                return;
            }
        }

        this.numberOfSeed--;
        if (this.numberOfSeed <= 0) {
            player.getInventory().getBackPack().removeItem(this);
            player.getIventoryBarItems().remove(this);
        }

        if (App.getInstance().getCurrentGame().isOnline()) {
            ClientNetworkManager.getInstance().sendMessage(new PlantSeedMessage(
                player.getName(),
                tile.getPosition().getX(),
                tile.getPosition().getY(),
                isTreePlanted,
                seedType.getName()
            ));
        }
    }
}
