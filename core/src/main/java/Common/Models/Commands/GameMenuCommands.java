package Common.Models.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements Commands{

    //New game
    NEW_GAME("game new -u (?<username1>[\\S]+) (?<username2>[\\S]+) (?<username3>[\\S]+)"),
    CHOOSE_MAP("game map (?<mapNumber>1|2)"),
    ;


    private final String pattern;
    private final Pattern compiledPattern;

    GameMenuCommands(String pattern) {
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
