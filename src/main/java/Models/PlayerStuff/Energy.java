package Models.PlayerStuff;

public class Energy {

    public final static int startingEnergy = 200;

    int energy;



    public Energy(int energy) {
        this.energy = startingEnergy;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

}
