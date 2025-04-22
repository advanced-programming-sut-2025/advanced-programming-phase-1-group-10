package src.Models.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Commands {
    String getPattern();

    default Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(getPattern());
        Matcher matcher = pattern.matcher(input);
        return matcher.matches() ? matcher : null;
    }
}