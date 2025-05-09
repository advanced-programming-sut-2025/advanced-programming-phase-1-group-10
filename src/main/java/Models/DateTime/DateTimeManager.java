package Models.DateTime;

import Models.Game;
import Models.Result;

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

    public Result showGameTime(Game game) {
        return new Result(true, "Game Time: " + game.getGameTime().getHour() + ":00");
    }

    public Result showGameDate(Game game) {
        return new Result(true, "Game Date: " + game.getGameTime().getYear() + "-" + game.getGameTime().getMonth() + "-" + game.getGameTime().getDay());
    }

    public Result showGameDateTime(Game game) {
        return new Result(true, showGameTime(game) + "\n" + showGameDate(game));
    }

    public Result showDayOfWeek(Game game) {
        String[] weekdays = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        return new Result(true,"Day of week: " + weekdays[game.getGameTime().getDay() % weekdays.length]);
    }

    public Result showSeason(Game game) {
        return new Result(true, "Season: " + game.getGameTime().getSeason().getName());
    }



}
