package src.Models;

import src.Models.Tools.BackPack;
import src.Models.Tools.Tool;

public class Player {


    private Position position;

    private int gold;
    private int wood;
    private Energy energy;
    private Player couple;
    private Tool currentTool;
    private BackPack backPack;
    private FarmType farmType;
    //TODO Animals


    private boolean faint;
    private int miningAbility;
    private int farmingAbility;
    private int foragingAbility;
    private int fishingAbility;

}
