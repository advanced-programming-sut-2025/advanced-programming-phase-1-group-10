package Models.NPC;

import Models.Person;
import Models.Place.NpcHosue;
import Models.Position;

public class NPC implements Person {

    private String name;
    private Position position;
    private final NpcHosue hosue;

    public NPC(String name, Position position, NpcHosue hosue) {
        this.name = name;
        this.position = position;
        this.hosue = hosue;
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
}
