package Models.PlayerStuff;

public class Energy {
    public static int MAX_ENERGY_AMOUNT = 200;
    int energyAmount;

    public Energy(int energy) {
        this.energyAmount = energy;
    }

    public int getEnergyAmount() {
        return energyAmount;
    }

    public void setEnergyAmount(int energyAmount) {
        this.energyAmount = energyAmount;
    }

    public boolean isFainted(int amount){
        return this.energyAmount - amount <= 0;
    }

    public void decreaseEnergy(int amount){
        this.energyAmount -= amount;
        if(this.energyAmount < 0){
            this.energyAmount = 0;
        }
    }

    public void increaseEnergy(int amount){
        this.energyAmount += amount;
        if(this.energyAmount > 200){
            this.energyAmount = 200;
        }
    }
}
