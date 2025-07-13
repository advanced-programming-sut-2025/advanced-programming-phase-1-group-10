package Models.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterMenuCommands implements Commands{

    MENU_EXIT("^menu\\s+exit$"),
    SHOW_CURRENT_MENU("^show\\s+current\\s+menu$"),
    REGISTER("^register\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)\\s+" +
            "(?<passwordconfirm>\\S+)\\s+-n\\s+(?<nickname>\\S+)" +
            "\\s+-e\\s+(?<email>\\S+)\\s+-g\\s+(?<gender>\\S+)$"),
    USERNAME("^[a-zA-Z0-9-]+$"),
    EMAIL("^[a-zA-Z0-9](?!.*[.]{2})[a-zA-Z0-9._-]*[a-zA-Z0-9]@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]{2,})+$"),
    PICK_QUESTION("^pick\\s+question\\s+-q\\s+(?<questionnumber>-?\\d+)\\s+-a\\s+" +
                "(?<answer>.+)\\s+-c\\s+(?<answerconfirm>.+)$"),
    LOGIN("^login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)(?:\\s+(?<stayLoggedIn>-stay-logged-in))?$"),
    FORGET_PASSWORD("^forget\\s+password\\s+-u\\s+(?<username>.+)$"),
    MENU_ENTER("^menu\\s+enter\\s+(?<menuname>.+)$")
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
