package Models.NPC;

import Models.Place.NpcHosue;
import Models.Position;

public class Abigel extends NPC {
    public Abigel(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }


    @Override
    public String getSymbol() {
        return "A";
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public NpcHosue getHosue() {
        return super.getHosue();
    }
}
