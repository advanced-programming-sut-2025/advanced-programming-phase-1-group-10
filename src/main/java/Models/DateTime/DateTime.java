package Models.DateTime;

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

    public DateTime(Season season, int year, int month, int day, int hour) {
        this.season = season;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
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
    

}