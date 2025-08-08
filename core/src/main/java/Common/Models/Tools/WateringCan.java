package Common.Models.Tools;

import Client.Assets.ToolAsset;
import Client.Network.ClientNetworkManager;
import Common.Models.App;
import Common.Models.Place.Lake;
import Common.Models.PlayerStuff.Player;
import Common.Models.Tile;
import Common.Network.Messages.Message;
import Common.Network.Messages.MessageTypes.WateringCanUsedMessage;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class WateringCan extends Tool {

    private int capacity;
    private int water;

    public WateringCan(Quality quality, int energyUsage) {
        super(quality, energyUsage);
        this.capacity = getCapacityByQuality(quality) ;
    }

    @Override
    public String getName() {
        return "Watering Can";
    }

    @Override
    public Sprite show() {
        return ToolAsset.show(this);
    }

    @Override
    public Message use(Tile tile) {
        Player player = App.getInstance().getCurrentGame().getCurrentPlayer();
        boolean isUsed = false;
        if(tile.getPlace() instanceof Lake){
            this.water = this.capacity;
            isUsed = true;
        }

        if(tile.getisPlow()){
            player.setFarmingAbility(player.getFarmingAbility() + 10);
            tile.setWatered(true);
            this.water--;
            isUsed = true;
        }

        int energyCost = (int)( (getEnergyUsage() - getQuality().getValue()) * App.getInstance().getCurrentGame().getWeather().getToolEnergyModifer());
        player.getEnergy().setEnergyAmount(
                player.getEnergy().getEnergyAmount() - (isUsed ? energyCost : energyCost - 1)
        );

        ClientNetworkManager.getInstance().sendMessage(new WateringCanUsedMessage(
            tile.getPosition().getX(),
            tile.getPosition().getY(),
            tile.isWatered()
        ));

        return null;
    }

    public int getCapacityByQuality(Quality quality) {
        switch (quality){
            case STARTER: this.capacity = 40 ;break;
            case COPPER: this.capacity = 55 ; break;
            case STEEL: this.capacity = 70 ; break;
            case GOLD : this.capacity =  85; break;
            case IRIDIUM: this.capacity =  100 ; break;
        }
        assert false : "invalid quality";
        return -1;
    }

    @Override
    public void setQuality(Quality quality) {
        super.setQuality(quality);
        this.capacity = getCapacityByQuality(quality);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }
}
