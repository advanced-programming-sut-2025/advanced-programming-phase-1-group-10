package Models.Tools;

import Models.App;
import Models.PlayerStuff.Player;
import Models.Tile;
import Models.TileType;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Hoe extends Tool {

    public Hoe(Quality quality, int energyUsage) {
        super(quality, energyUsage);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Sprite show() {
        return toolAsset.show(this);
    }

    @Override
    public void use(Tile tile) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        int energyCost = (int)( (getEnergyUsage() - getQuality().getValue()) * App.getInstance().getCurrentGame().getWeather().getToolEnergyModifer());
        if(tile.getPlace() == null && tile.getItem() == null && tile.getFarm() != null && tile.getPerson() == null && tile.getTileType() == TileType.Grass){
            tile.setPlow(true);
        }
        player.getEnergy().setEnergyAmount(
                player.getEnergy().getEnergyAmount() - energyCost
        );
    }
}
