package src.Models.DateTime;

public class DateTime {

    Season season;
    private int year;
    private int month;
    private int day;
    private int hour;

    public void nextDay(){}
    public void nextMonth(){}
    public void nextYear(){}
    public void nextSeason(){}
    public void nextHour(){}

    public void setUpDateTime(){
        this.year = 1990;
        this.month = 1;
        this.season = Season.SPRING;
        this.day = 1;
        this.hour = 9;
    }


    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public Season getSeason() {
        return season;
    }

    private static DateTime instance;
    public static DateTime getInstance(){
        if(instance == null){
            instance = new DateTime();
        }
        return instance;
    }

}