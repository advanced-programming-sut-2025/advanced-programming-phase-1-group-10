package Common.Models.Tools;

import Client.Network.Send.MessageTypes.Message;
import Common.Models.App;
import Common.Models.PlayerStuff.Player;
import Common.Models.Tile;
import Common.Models.TileType;
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
    public Message use(Tile tile) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        int energyCost = (int)( (getEnergyUsage() - getQuality().getValue()) * App.getInstance().getCurrentGame().getWeather().getToolEnergyModifer());
        if(tile.getPlace() == null && tile.getItem() == null && tile.getFarm() != null && tile.getPerson() == null && tile.getTileType() == TileType.Grass){
            tile.setPlow(true);
        }
        player.getEnergy().setEnergyAmount(
                player.getEnergy().getEnergyAmount() - energyCost
        );
        return null;
    }
}
