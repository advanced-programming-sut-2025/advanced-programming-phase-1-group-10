package Models.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands implements Commands {

    //Map print commands
    PRINT_MAP("^print map$"),

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
    SHOW__CURRENT_TOOL("^tools show current$"),
    UPGRADE_TOOL("tools upgrade (?<toolName>[\\S ]+)"),
    TOOL_USE("tools use -d (?<direction>)[\\S]+"),

    //Crafting Commands
    CRAFTING_SHOW_RECIPES("^crafting show recipes$"),
    CRAFTING_CRAFT("crafting craft (?<itemName>[\\S ]+)"),

    // animals
    BUILD_MAINTENANCE("^build\\s+-a\\s+(?<buildingName>[^\\s]+)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)$"),
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
