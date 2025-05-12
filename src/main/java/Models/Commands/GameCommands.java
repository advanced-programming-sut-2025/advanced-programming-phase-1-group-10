package Models.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands implements Commands {

    //Map print commands
    PRINT_MAP("^print map$"),
    PRINT_PART_OF_MAP("^print map -l\\s*<\\s*(?<x>-?\\d+)\\s*,\\s*(?<y>-?\\d+)>\\s*-s\\s*(?<size>\\d+)$"),

    //Move commands
    WALK("^walk -l\\s*<\\s*(?<x>-?\\d+)\\s*,\\s*(?<y>-?\\d+)\\s*>$"),

    //Time Commands
    SHOW_TIME("^time$"),
    SHOW_DATE("^date$"),
    SHOW_DATETIME("^datetime$"),
    SHOE_DAY_OF_WEEK("^dayOfWeek$"),

    //Weather Commands
    SHOW_WEATHER("^weather$"),
    SHOW_WEATHER_FORECAST("^weather forecast$"),

    //Energy Commands
    SHOW_ENERGY("^energy show$"),

    //Invetory Commands
    SHOW_INVENTORY("^inventory show$"),
    DELETE_ITEM_FROM_INVENTORY("^inventory trash -i (?<itemName>[\\S ]+) -n (?<number>[\\S]+)$"),

    //Tool Commands
    EQUIP_TOOL("^tools equip (?<toolName>[\\S ]+)$"),
    SHOW_CURRENT_TOOL("^tools show current$"),
    UPGRADE_TOOL("tools upgrade (?<toolName>[\\S ]+)"),
    TOOL_USE("tools use -d (?<direction>[\\S]+)"),
    SHOW_AVALIABLE_TOOL("^tools show available$"),

    //Crafting Commands
    CRAFTING_SHOW_RECIPES("^crafting show recipes$"),
    CRAFTING_CRAFT("crafting craft (?<itemName>[\\S ]+)"),

    //NPC Commands
    MEET_NPC("^meet NPC (?<npcName>[\\S]+)$"),
    GIFT_NPC("^gift NPC (?<npcName>[\\S]+) -i (?<item>[\\S]+)$"),
    SHOW_NPC_FRIENDSHIP("^friendship NPC list$"),

    // Animals Commands
    BUILD_MAINTENANCE("^build\\s+-a\\s+(?<buildingName>[^\\s]+)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)$"),
    BUY_ANIMAL("^buy\\s+animal\\s+-a\\s+(?<animal>[^\\s]+)\\s+-n\\s+(?<name>[^\\s]+)$"),
    PET_ANIMAL("^pet\\s+-n\\s+(?<name>[^\\s]+)"),
    ANIMLAS("animals");
    ;

    private final String pattern;
    private final Pattern compiledPattern;

    GameCommands(String pattern) {
        this.pattern = pattern;
        this.compiledPattern = Pattern.compile(pattern);
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = compiledPattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }

}
