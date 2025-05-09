package Models.PlayerStuff;


import Models.Animal.Animal;
import Models.Farm;
import Models.Item;
import Models.Person;
import Models.Position;
import Models.Tools.Tool;


import java.util.ArrayList;

public class Player implements Person {

    private String name;

    private Position position;

    private Farm farm;

    private ArrayList<Animal> playerAnimals = new ArrayList<>();

    private int gold;
    private int wood;
    private Energy energy;
    private boolean isFainted;
    private Player couple;
    private Tool currentTool;
    private Inventory inventory;


    private int miningAbility;
    private int farmingAbility;
    private int foragingAbility;
    private int fishingAbility;

    private ArrayList<TradeRequest> acceptedTradeRequests;

    private Trading trading;

    public Player(String name) {
        this.name = name;
        this.energy = new Energy(Energy.MAX_ENERGY_AMOUNT);
        this.isFainted = false;
        //this.position = new Position();
        //TODO Add starting items for player
    }

    public void setEnergy(int energyAmount) {
        //Faint process implements here.
    }

    public Energy getEnergy() {
        return energy;
    }

    public void talkPerson(Person person) {
        //Increase friendship amount.
    }

    public void sendGift(Item gift, Person person) {
        //Send gift to a Person
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public int getGold() {
        return gold;
    }

    public int getWood() {
        return wood;
    }

    public Player getCouple() {
        return couple;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isFainted() {
        return isFainted;
    }

    public int getMiningAbility() {
        return miningAbility;
    }

    public int getFarmingAbility() {
        return farmingAbility;
    }

    public int getForagingAbility() {
        return foragingAbility;
    }

    public int getFishingAbility() {
        return fishingAbility;
    }

    public ArrayList<TradeRequest> getAcceptedTradeRequests() {
        return acceptedTradeRequests;
    }

    public Trading getTrading() {
        return trading;
    }

    public ArrayList<Animal> getPlayerAnimals() {
        return playerAnimals;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public void setFainted(boolean fainted) {
        isFainted = fainted;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
