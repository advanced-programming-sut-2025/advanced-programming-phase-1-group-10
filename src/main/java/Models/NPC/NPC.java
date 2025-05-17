package Models.NPC;

import Models.*;
import Models.Place.NpcHosue;

import java.util.ArrayList;

public class NPC implements Person {

    private String name;
    private Position position;
    private final NpcHosue hosue;
    //TODO Add this
    private final ArrayList<Item> favoriteItems;
    private final ArrayList<Quest> quests = new ArrayList<>();

    public NPC(String name, Position position, NpcHosue hosue) {
        this.name = name;
        this.position = position;
        this.hosue = hosue;
        this.favoriteItems =  new ArrayList<>();
    }

    @Override
    public String getSymbol() {
        //Never Prints
        return "!";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public NpcHosue getHosue() {
        return hosue;
    }

    public ArrayList<Item> getFavoriteItems() {
        return favoriteItems;
    }

    public String talk(){
        //This must not happen.
        return "";
    }

    public boolean hasRelationWithNPC(String npcName) {
        return App.getInstance()
                .getCurrentGame()
                .getCurrentPlayer()
                .getNpcRelations()
                .stream()
                .anyMatch(npc -> npc.getNpc().getName().equals(npcName));
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }
}
