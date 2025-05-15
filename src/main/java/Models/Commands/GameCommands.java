package Models.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands implements Commands {

    //Trading
    START_TRADE("^start trade$"),
    TRADE_OFFER("^trade -u (?<username>[\\S]+) -t offer -i (?<item>[\\S ]+) -a (?<amount>[\\S ]+) -p (?<price>[\\S]+)$"),
    TRADE_REQUEST("^trade -u (?<username>[\\S]+) -t request -i (?<item>[\\S ]+) -a (?<amount>[\\S]+) -ti (?<targetItem>[\\S ]+) -ta (?<targetAmount>[\\S]+)$"),
    LIST_TRADE("^trade list$"),
    RESPOND_TRADE("trade response -(?<state>accept|reject) -i (?<id>[\\S]+)"),
    TRADE_HISTORY("^$trade history"),

    //Frinedship
    SHOW_FRINEDSHIP("^friendships$"),
    TALK_TO_PLAYER("^talk -u (?<username>[\\S]+) -m (?<message>[\\S ]+)$"),
    TALK_HISTORY("^talk history -u (?<username>[\\S]+)$"),
    SEND_GIFT("gift -u (?<username>[\\S]+) -i (?<item>[\\S ]+) -a (?<amount>[\\S]+)"),
    SHOW_GIFT_LIST("gift list"),
    RATE_GIFT("^gift rate -i (?<giftNumber>[\\S]+) -r (?<rate>[\\S]+)$"),
    GIFT_HISTORY("^gift history -u (?<username>[\\S]+)$"),
    HUG_EACH_OHTER("^hug -u (?<username>[\\S]+)$"),
    GIVE_FLOWER("^flower -u (?<username>[\\S]+)$"),
    ASK_MARRIAGE("^ask marriage -u (?<username>[\\S]+)$"),

    //Map print commands
    PRINT_MAP("^print map$"),
    PRINT_PART_OF_MAP("^print map -l\\s*<\\s*(?<x>-?\\d+)\\s*,\\s*(?<y>-?\\d+)>\\s*-s\\s*(?<size>\\d+)$"),
    HELP_READING_MAP("^help reading map$"),

    //Move commands
    WALK("^walk -l\\s*<\\s*(?<x>-?\\d+)\\s*,\\s*(?<y>-?\\d+)\\s*>$"),

    //Time Commands
    SHOW_TIME("^time$"),
    SHOW_DATE("^date$"),
    SHOW_DATETIME("^datetime$"),
    SHOW_DAY_OF_WEEK("^dayOfWeek$"),

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

    //Cooking Commands
    COOKING_SHOW_RECIPES("^cooking show recipes$"),
    COOKING_REFRIGERATOR("cooking refrigerator (?<action>put|pick) (?<item>[\\S ]+)"),
    COOKING_PREPARE("cooking prepare (?<recipeName>[\\S ]+)"),

    //NPC Commands
    MEET_NPC("^meet NPC (?<npcName>[\\S]+)$"),
    GIFT_NPC("^gift NPC (?<npcName>[\\S]+) -i (?<item>[\\S]+)$"),
    SHOW_NPC_FRIENDSHIP("^friendship NPC list$"),

    // Animals Commands
    BUILD_MAINTENANCE("^build\\s+-a\\s+(?<buildingName>[^\\s]+)\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)$"),
    BUY_ANIMAL("^buy\\s+animal\\s+-a\\s+(?<animal>[^\\s]+)\\s+-n\\s+(?<name>[^\\s]+)$"),
    PET_ANIMAL("^pet\\s+-n\\s+(?<name>[^\\s]+)"),
    ANIMLAS("animals"),
    SHEPHERD_ANIMAL("^shepherd\\s+animals\\s+-n\\s+(?<name>[^\\s]+)\\s+-l\\s*<\\s*(?<x>-?\\d+)\\s*,\\s*(?<y>-?\\d+)\\s*>$"),
    FEED_ANIMAL_WITH_HAY("^feed\\s+hay\\s+-n\\s+(?<name>[^\\s]+)$"),
    SELL_ANIMAL("^sell\\s+animal\\s+-n\\s+(?<name>[^\\s]+)$"),
    FISHING_POLE("^fishing\\s+-p\\s+(?<fishingpole>.+)$"),
    COLLECT_PRODUCTS("^collect\\s+produce\\s+-n\\s+(?<name>[^\\s]+)$"),
    PRODUCES("^produces$"),

    //Farming Commands
    CRAFT_INFO("^craftinfo\\s+-n\\s+(?<name>.+)$"),
    PLANT("^plant\\s+-s\\s+(?<seed>.+)\\s+-d\\s+(?<direction>.+)$"),
    SHOW_PLANT("^showplant\\s+-l\\s*<\\s*(?<x>-?\\d+)\\s*,\\s*(?<y>-?\\d+)\\s*>$"),
    FERTILIZER("^fertilize\\s+-f\\s+(?<fertilizer>.+)\\s+-d\\s+(?<direction>.+)$"),
    HOW_MACH_WATER("^how much water$"),
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
