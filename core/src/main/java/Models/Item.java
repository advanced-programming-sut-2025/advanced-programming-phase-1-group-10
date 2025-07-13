package Models;

public interface Item{

    String getName();
    String getSymbol();
    int getNumber();
    void setNumber(int number);
    Item copyItem(int number);

}
