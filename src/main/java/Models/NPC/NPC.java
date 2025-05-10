package Models.NPC;

import Models.Person;

public class NPC implements Person {



    private String name;


    @Override
    public String getSymbol() {
        //Never Prints
        return "!";
    }
}
