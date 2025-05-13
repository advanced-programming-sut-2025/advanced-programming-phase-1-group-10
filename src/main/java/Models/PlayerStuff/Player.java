package Models.PlayerStuff;


import Models.*;
import Models.Animal.Animal;
import Models.FriendShip.Friendship;
import Models.FriendShip.Gift;
import Models.Recipe.Recipe;
import Models.Tools.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Player implements Person {

    private String name;
    private Gender gender;

    private Position position;

    private Farm farm;

    private ArrayList<Animal> playerAnimals = new ArrayList<>();
    private ArrayList<NPCRelation> npcRelations = new ArrayList<>();
    private ArrayList<Friendship> friendships = new ArrayList<>();
    private ArrayList<Gift>  recievedGifts = new ArrayList<>();

    private int gold = 0;
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
        this.gender = ThreadLocalRandom.current().nextBoolean() ? Gender.Male : Gender.Female;
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

    public void setCouple(Player couple) {
        this.couple = couple;
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

    public void addGold(int amount){
        gold += amount;
    }

    private int calculateLevel(int xp) {
        int level = 0;
        while (level < 4 && xp >= 50 + level * 100) {
            level++;
        }
        return level;
    }

    public int getMiningLevel() {
        return calculateLevel(miningAbility);
    }

    public int getFarmingLevel() {
        return calculateLevel(farmingAbility);
    }

    public int getForagingLevel() {
        return calculateLevel(foragingAbility);
    }

    public int getFishingLevel() {
        return calculateLevel(fishingAbility);
    }

    public ArrayList<NPCRelation> getNpcRelations() {
        return npcRelations;
    }

    public ArrayList<Friendship> getFriendships() {
        return friendships;
    }

    public void setNpcRelations(ArrayList<NPCRelation> npcRelations) {
        this.npcRelations = npcRelations;
    }

    @Override
    public String getSymbol() {
        return "P";
    }

    public ArrayList<Gift> getRecievedGifts() {
        return recievedGifts;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
