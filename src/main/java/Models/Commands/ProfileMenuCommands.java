package Models.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands implements Commands {

    CHANGE_USERNAME("^change username\\s+-u\\s+(?<username>.+)$"),
    USERNAME("^[a-zA-Z0-9-]+$"),
    CHANGE_PASSWORD("^change password\\s+-p\\s+(?<new_password>.+)\\s+-o\\s+(?<old_password>.+)$"),
    CHANGE_EMAIL("^change email\\s+-e\\s+(?<email>[^\\s]+)$"),
    EMAIL("^[a-zA-Z0-9](?!.*[.]{2})[a-zA-Z0-9._-]*[a-zA-Z0-9]@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]{2,})+$"),
    CHANGE_NICKNAME("^change nickname\\s+-u\\s+(?<nickname>.+)$"),
    USER_INFO("^user\\s+info$")
    ;

    private final String pattern;
    private final Pattern compiledPattern;

    ProfileMenuCommands(String pattern) {
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
