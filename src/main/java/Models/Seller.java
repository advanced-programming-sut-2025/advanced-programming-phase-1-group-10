package Models;

public class Seller implements Person {

    private String name;
    private final String symbol;
    private final Position position;


    public Seller(String name, String symbol, Position position) {
        this.name = name;
        this.symbol = symbol;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public Position getPosition() {
        return position;
    }


}
