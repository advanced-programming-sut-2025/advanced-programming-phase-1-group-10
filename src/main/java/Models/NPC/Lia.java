package Models.NPC;

import Models.Place.NpcHosue;
import Models.Position;

public class Lia extends NPC {
    public Lia(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    @Override
    public String getSymbol() {
        return "L";
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
