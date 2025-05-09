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
    private Energy energy;
    private Player couple;
    private Tool currentTool;
    private Inventory inventory;

    private boolean faint;
    private int miningAbility;
    private int farmingAbility;
    private int foragingAbility;
    private int fishingAbility;

    private ArrayList<TradeRequest> acceptedTradeRequests;

    private Trading trading;

    public Player(String name) {
        this.name = name;
        this.inventory = new Inventory();
        this.gold = 0;
        this.energy = new Energy(Energy.startingEnergy);
        this.couple = null;
        this.currentTool = null;
        this.faint = false;
        this.miningAbility = 0;
        this.farmingAbility = 0;
        this.foragingAbility = 0;
        this.fishingAbility = 0;
        this.acceptedTradeRequests = new ArrayList<>();
        this.trading = null;
    }

    public void setEnergy(int energy) {
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

    public boolean isFaint() {
        return faint;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }

    public void setCouple(Player couple) {
        this.couple = couple;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setFaint(boolean faint) {
        this.faint = faint;
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

    public void setTrading(Trading trading) {
        this.trading = trading;
    }
}
