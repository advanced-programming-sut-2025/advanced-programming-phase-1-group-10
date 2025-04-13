package src.Models.FriendShip;


import src.Models.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class Friendship {

    private int xp ;
    private int level;

    private ArrayList<String> message = new ArrayList<>();
    private ArrayList<Gift> gifts = new ArrayList<>();
    private HashMap<Item, Integer> givenGifts = new HashMap<>();
}
