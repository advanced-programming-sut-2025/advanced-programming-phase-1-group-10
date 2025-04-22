package src.Models.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterMenuCommands implements Commands{

    MENU_EXIT("^menu\\s+exit$"),
    SHOW_CURRENT_MENU("^show\\s+current\\s+menu$"),
    MENU_ENTER("^menu\\s+enter\\s+<(?<menu_name>.+)>$"),
    REGISTER("^register\\s+-u\\s+<(?<username>\\S+)>\\s+-p\\s+<(?<password>\\S+)>\\s+" +
            "<(?<password_confirm>\\S+)>\\s+-n\\s+<(?<nickname>\\S+)>" +
            "\\s+-e\\s+<(?<email>\\S+)>\\s+-g\\s+<(?<gender>\\S+)>$"),
    USERNAME("^[a-zA-Z0-9-]+$"),
    PASSWORD("^[a-z0-9?><,\"';:/|[\\]{}+=)(*&^%$#!]{8,20}$"),
    EMAIL("^[a-zA-Z0-9](?!.*[.]{2})[a-zA-Z0-9._-]*[a-zA-Z0-9]@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]{2,})+$"),
    ;

    private final String pattern;
    private final Pattern compiledPattern;

    RegisterMenuCommands(String pattern) {
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
