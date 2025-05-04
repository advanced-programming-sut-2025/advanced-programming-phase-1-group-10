package Models.Tools;

import Models.Item;

public class Tool implements Item {
    private String name;
    private int energyUsage;
    private Quality quality;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumber() {
        return 1;
    }

    @Override
    public void setNumber(int number) {
        //Must not be coded, because number of tool is constant (one per player);
    }


}
