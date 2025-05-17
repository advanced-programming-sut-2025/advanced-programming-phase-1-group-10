package Models.Tools;

import Models.App;
import Models.Place.Lake;
import Models.PlayerStuff.Player;
import Models.Tile;

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
    public void use(Tile tile) {
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

    }

    public int getCapacityByQuality(Quality quality) {
        switch (quality){
            case STARTER: this.capacity = 40 ;break;
            case BRONZE : this.capacity = 55 ; break;
            case SILVER : this.capacity = 70 ; break;
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
