package Common.Models.Tools;

import Client.Assets.ToolAsset;
import Client.Controllers.MessageSystem;
import Common.Models.App;
import Common.Models.Item;
import Common.Models.Planets.Crop.Crop;
import Common.Models.Planets.Crop.CropTypeNormal;
import Common.Models.Planets.Crop.ForagingCropType;
import Common.Models.Planets.Fruit;
import Common.Models.Planets.Tree;
import Common.Models.PlayerStuff.Player;
import Common.Models.Tile;
import Common.Network.Messages.Message;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Seythe extends Tool {
    public Seythe(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    @Override
    public Sprite show() {
        return ToolAsset.show(this);
    }

    @Override
    public String getName() {
        return "Seythe";
    }

    @Override
    public Message use(Tile tile){
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        player.setEnergy((int) (player.getEnergy().getEnergyAmount() - 2));
        if(tile.getItem() instanceof Crop){
            if(((Crop) tile.getItem()).getCropType() instanceof ForagingCropType){
                Crop crop = (Crop) tile.getItem();
                boolean added = player.getInventory().getBackPack().addItem(crop);
                if(added){
                    crop.setShowCrop(false);
                    tile.setCrop(null);
                    tile.setItem(null);
                    player.setForagingAbility(player.getForagingAbility() + 10);
                    MessageSystem.showInfo(crop.getName() + " added to backpack!",3.0f);
                }
            }
            else if(((Crop) tile.getItem()).getCropType() instanceof CropTypeNormal){
                Crop crop = (Crop) tile.getItem();
                if (crop.isHarvestable()) {
                    Item harvestedItem = crop.harvestCrop();
                    if (harvestedItem != null) {
                        boolean added = App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().addItem(harvestedItem);
                        if (added) {
                            MessageSystem.showInfo(harvestedItem.getName() + " harvested!", 3.0f);
                        }

                        if (crop.getCropType() instanceof CropTypeNormal) {
                            CropTypeNormal normalCrop = (CropTypeNormal) crop.getCropType();
                            if (normalCrop.isOneTime()) {
                                tile.setItem(null);
                                tile.setCrop(null);
                            }
                        }
                    }
                }
                else
                    MessageSystem.showWarning("This crop has no fruit to harvest yet!",3.0f);
            }
        }
        else if(tile.getItem() instanceof Tree){
            Tree tree = (Tree) tile.getItem();
            if (tree.hasFruits()) {
                Fruit harvestedFruit = tree.harvestFruit();
                if (harvestedFruit != null) {
                    boolean added = App.getInstance().getCurrentGame().getCurrentPlayer().getInventory().getBackPack().addItem(harvestedFruit);
                    if(added) {
                        MessageSystem.showInfo("Fruit " + harvestedFruit.getName() + " harvested!", 3.0f);
                    }
                }
            }
            else
                MessageSystem.showWarning("This tree has no fruit to harvest yet!",3.0f);
        }
        return null;
    }
}
