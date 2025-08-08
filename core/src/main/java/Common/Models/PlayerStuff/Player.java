package Common.Models.PlayerStuff;


import Common.Models.*;
import Common.Models.Tools.*;
import Common.Models.Animal.Animal;
import Common.Models.FriendShip.Friendship;
import Common.Models.FriendShip.Gift;
import Common.Models.FriendShip.Message;
import com.badlogic.gdx.graphics.g2d.Sprite;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Player implements Person {

    public static int PLAYER_WIDTH = 32;
    public static int PLAYER_HEIGHT = 64;

    public static int  PLAYER_INENTORY_BAR_SIZE = 12;

    private final String name;
    private Gender gender;

    public static final int STARTING_GOLD = 43200;


    private final ArrayList<Item> iventoryBarItems;
    private int selectedSlot;

    private float x, y;
    private float speed = 500f;
    private float stateTime;
    private Direction direction = Direction.DOWN;
    private boolean moving = false;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Position position;
    private Farm farm;

    private final ArrayList<Animal> playerAnimals = new ArrayList<>();
    private final ArrayList<NPCRelation> npcRelations = new ArrayList<>();
    private final ArrayList<Friendship> friendships = new ArrayList<>();
    private final ArrayList<Gift>  recievedGifts = new ArrayList<>();
    private final ArrayList<Gift> sendedGifts = new ArrayList<>();
    private final ArrayList<TradeRequest> tradeRequests = new ArrayList<>();
    private final ArrayList<Message>  recievedMessages = new ArrayList<>();

    private int gold;
    private final Energy energy;
    private boolean isFainted;
    private Player couple;
    private Tool currentTool;
    private final Inventory inventory;

    private int miningAbility;
    private int farmingAbility;
    private int foragingAbility;
    private int fishingAbility;

    private Quest currentQuest;


    public Player(String name , long seed) {
        this.name = name;
        this.energy = new Energy(Energy.MAX_ENERGY_AMOUNT);
        this.isFainted = false;
        this.inventory = new Inventory();
        this.gold = STARTING_GOLD;
        this.iventoryBarItems = new ArrayList<>();
        this.iventoryBarItems.addAll(Arrays.asList(
                new Hoe(Quality.STARTER,5),
                new Pickaxe(Quality.STARTER,5),
                new Axe(Quality.STARTER,5),
                new WateringCan(Quality.STARTER,5),
                new Seythe(Quality.STARTER,2),
                new FishingPole(Quality.STARTER,5),
                new Shear(Quality.STARTER,2),
                new MilkPail(Quality.STARTER,2)
        ));
        this.gender = new Random(seed).nextBoolean() ? Gender.Male : Gender.Female;
    }



    public void setEnergy(int energyAmount) {}

    public Energy getEnergy() {
        return energy;
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
        while (level < 5 && xp >= 50 + level * 100) {
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

    @Override
    public Sprite show() {
        return null;
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

    public ArrayList<TradeRequest> getTradeRequests() {
        return tradeRequests;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public ArrayList<Item> getIventoryBarItems() {
        return iventoryBarItems;
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public Quest getCurrentQuest() {
        return currentQuest;
    }

    public void setCurrentQuest(Quest currentQuest) {
        this.currentQuest = currentQuest;
    }

    public ArrayList<Message> getRecievedMessages() {
        return recievedMessages;
    }

    public ArrayList<Gift> getSendedGifts() {
        return sendedGifts;
    }
}
