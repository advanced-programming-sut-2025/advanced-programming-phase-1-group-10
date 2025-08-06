package Common.Models.PlayerStuff;

public class Energy {
    public static int MAX_ENERGY_AMOUNT = 200;
    private double energyAmount;

    public Energy(double energy) {
        this.energyAmount = energy;
    }

    public double getEnergyAmount() {
        return energyAmount;
    }

    public void setEnergyAmount(double energyAmount) {
        this.energyAmount = energyAmount;
    }

    public boolean isFainted(double amount){
        return this.energyAmount - amount <= 0;
    }

    public void decreaseEnergy(double amount){
        this.energyAmount -= amount;
        if(this.energyAmount < 0){
            this.energyAmount = 0;
        }
    }

    public void increaseEnergy(double amount){
        this.energyAmount += amount;
        if(this.energyAmount > 200){
            this.energyAmount = 200;
        }
    }
}
