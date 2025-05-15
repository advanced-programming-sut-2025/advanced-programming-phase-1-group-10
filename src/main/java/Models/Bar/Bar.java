package Models.Bar;

import Models.Item;

public class Bar implements Item {

    private BarType barType;
    private int numberOfBar;

    public Bar(BarType barType, int numberOfBar) {
        this.barType = barType;
        this.numberOfBar = numberOfBar;
    }

    @Override
    public String getName(){
        return barType.getName();
    }

    @Override
    public String getSymbol() {
        return "Ba";
    }

    @Override
    public int getNumber() {
        return numberOfBar;
    }

    @Override
    public void setNumber(int number) {
        numberOfBar = number;
    }

    @Override
    public Item copyItem(int number) {
        return new Bar(barType, number);
    }


}
