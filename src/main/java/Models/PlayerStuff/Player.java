package Models.PlayerStuff;


import Models.Animal.Animal;
import Models.Farm;
import Models.Item;
import Models.Person;
import Models.Position;
import Models.Recipe.Recipe;
import Models.Tools.*;


import java.util.ArrayList;
import java.util.Arrays;

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
        this.inventory = new Inventory();
        this.inventory.getBackPack().getItems().addAll(Arrays.asList(
                new Hoe(Quality.STARTER,5),
                new Pickaxe(Quality.STARTER,5),
                new Axe(Quality.STARTER,5),
                new WateringCan(Quality.STARTER,5),
                new Seythe(Quality.STARTER,2)
        ));
        //this.position = new Position();
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

    public void setMiningAbility(int miningAbility) {
        this.miningAbility = miningAbility;
    }

    public void setFarmingAbility(int farmingAbility) {
        this.farmingAbility = farmingAbility;
    }

    public void setForagingAbility(int foragingAbility) {
        this.foragingAbility = foragingAbility;
    }

    public void setFishingAbility(int fishingAbility) {
        this.fishingAbility = fishingAbility;
    }

    @Override
    public String getSymbol() {
        return "P";
    }
}
