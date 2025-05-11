package Models.NPC;

import Models.Place.NpcHosue;
import Models.Position;

public class Harvey extends NPC {
    public Harvey(String name, Position position, NpcHosue hosue) {
        super(name, position, hosue);
    }

    @Override
    public String getSymbol() {
        return "H";
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
