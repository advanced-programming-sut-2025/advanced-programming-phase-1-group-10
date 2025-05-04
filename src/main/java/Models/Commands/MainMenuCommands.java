package Models.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands implements Commands {

    SHOW_CURRENT_MENU("^show\\s+current\\s+menu$"),
    MENU_ENTER("^menu\\s+enter\\s+(?<menuname>.+)$"),
    USER_LOGOUT("^user\\s+logout$")
    ;


    private final String pattern;
    private final Pattern compiledPattern;

    MainMenuCommands(String pattern) {
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
