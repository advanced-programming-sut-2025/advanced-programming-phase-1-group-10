package src.Models.PlayerStuff;

import src.Models.Item;
import src.Models.Person;
import src.Models.Position;
import src.Models.Tools.BackPack;
import src.Models.Tools.Tool;

import java.util.ArrayList;

public class Player implements Person {


    private Position position;

    private int gold;
    private int wood;
    private Energy energy;
    private Player couple;
    private Tool currentTool;
    private BackPack backPack;

    private boolean faint;
    private int miningAbility;
    private int farmingAbility;
    private int foragingAbility;
    private int fishingAbility;

    private ArrayList<TradeRequest> acceptedTradeRequests;

    private Trading trading;

    public void setEnergy(int energy) {
        //Faint process implements here.
    }

    public void talkPerson(Person person) {
        //Increase friendship amount.
    }

    public void sendGift(Item gift, Person person) {
        //Send gift to a Person
    }

}
