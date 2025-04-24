package Models.DateTime;

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

    public Result showGameTime(){
        return new Result(true, "Game Time: " + DateTime.getInstance().getHour() + ":00");
    }

    public Result showGameDate(){
        return new Result(true, "Game Date: " + DateTime.getInstance().getYear() + "-" + DateTime.getInstance().getMonth() + "-" + DateTime.getInstance().getDay());
    }

    public Result showGameDateTime(){
        return new Result(true, showGameTime() + "\n" + showGameDate());
    }

    public Result showDayOfWeek(){
        String[] weekdays = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        return new Result(true,"Day of week: " + weekdays[DateTime.getInstance().getDay() % weekdays.length]);
    }

    public Result showSeason(){
        return new Result(true, "Season: " + DateTime.getInstance().getSeason().getName());
    }



}
