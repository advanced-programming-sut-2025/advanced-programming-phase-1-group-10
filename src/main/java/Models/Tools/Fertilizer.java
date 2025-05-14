package Models.Tools;

import Models.Item;

public class Fertilizer implements Item {
    private String name;
    private int price;

    public Fertilizer(String name, int price) {
        this.name = name;
        this.price = price;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSymbol() {
        return "";
    }

    @Override
    public int getNumber() {
        return 0;
    }

    @Override
    public void setNumber(int number) {

    }

    @Override
    public Item copyItem(int number) {
        return null;
    }

    public int getPrice() {
        return price;
    }
}
