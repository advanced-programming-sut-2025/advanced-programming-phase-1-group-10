package Models.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements Commands{

    //Time Commands
    SHOW_TIME("^time$"),
    SHOW_DATE("^date$"),
    SHOW_DATETIME("^datetime$"),
    SHOE_DAY_OF_WEEK("^dayOfWeek$"),

    //Weather Commands
    SHOW_WEATHER("^weather$"),
    SHOW_WEATHER_FORECAST("^weather forecast$"),

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
