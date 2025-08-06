package Common.Models.DateTime;

import Common.Models.Game;
import Common.Models.Result;

public class DateTimeManager {

    private static DateTimeManager instance;

    private DateTimeManager() {

    }

    public static DateTimeManager getInstance() {
        if(instance == null) {
            instance = new DateTimeManager();
        }
        return instance;
    }

    public static Result showGameTime(Game game) {
        return new Result(true, "Game Time: " + game.getGameTime().getHour() + ":00");
    }

    public static Result showGameDate(Game game) {
        return new Result(true, "Game Date: " + game.getGameTime().getYear() + "-" + game.getGameTime().getMonth() + "-" + game.getGameTime().getDay());
    }

    public static Result showGameDateTime(Game game) {
        return new Result(true, showGameTime(game) + "\n" + showGameDate(game));
    }

    public static Result showDayOfWeek(Game game) {
        String[] weekdays = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        return new Result(true,"Day of week: " + weekdays[(game.getGameTime().getDay() - 1) % weekdays.length]);
    }

    public static Result showSeason(Game game) {
        return new Result(true, "Season: " + game.getGameTime().getSeason().getName());
    }

}
