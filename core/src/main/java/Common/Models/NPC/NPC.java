package Common.Models.NPC;

import Common.Models.*;
import Common.Models.Place.NpcHosue;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public abstract class NPC implements Person {

    private final String name;
    private final Position position;
    private final NpcHosue hosue;
    private final ArrayList<Item> favoriteItems;
    private final ArrayList<Quest> quests = new ArrayList<>();

    public NPC(String name, Position position, NpcHosue hosue) {
        this.name = name;
        this.position = position;
        this.hosue = hosue;
        this.favoriteItems =  new ArrayList<>();
    }

    @Override
    public Sprite show() {
       return null;
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
