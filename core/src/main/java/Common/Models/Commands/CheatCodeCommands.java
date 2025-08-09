package Common.Models.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CheatCodeCommands implements Commands {

    CHEAT_HOUR("^cheat advance time (?<x>[\\S]+)h$"),
    CHEAT_DAY( "^cheat advance date (?<x>[\\S]+)d$"),

    CHANGE_NEXT_DAY_WEATHER("^cheat weather set (?<Type>[\\S]+)$"),
    ADD_MONEY("^cheat add (?<count>[\\S]+) dollars$"),

    ADD_SEED("^seed (?<seedname>.+)$"),
    ADD_FERTILIZER("^add fertilizer$"),
    GET_RING("^get ring$"),
    GET_BOUQUET("^get bouquet$"),
    SET_ENERGY("^energy set -v (?<value>[\\S]+)$"),
    SET_ENERGY_UNLIMITED("^energy unlimited$"),
    SET_ANIMAL_FRIENDSHIP("^cheat\\s+set\\s+friendship\\s+-n\\s+(?<animalName>[\\S]+)\\s+-c\\s+(?<amount>\\d+)$"),
    THOR_TILE("^cheat Thor -l\\s*<\\s*(?<x>-?\\d+)\\s*,\\s*(?<y>-?\\d+)\\s*>$"),
    ;

    private final String pattern;
    private final Pattern compiledPattern;

    CheatCodeCommands(String pattern) {
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
