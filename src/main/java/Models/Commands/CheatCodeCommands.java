package Models.Commands;

import Models.Person;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CheatCodeCommands implements Commands {

    SET_ENERGY("^energy set -v (?<value>[\\S]+)$"),
    SET_ENERGY_UNLIMITED("^energy unlimited$"),
    SET_ANIMAL_FRIENDSHIP("^cheat\\s+set\\s+friendship\\s+-n\\s+(?<animalName>[\\S]+)\\s+-c\\s+(?<amount>\\d+)$")
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
