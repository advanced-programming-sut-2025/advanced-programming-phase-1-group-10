package Models.DateTime;

public class DateTime {

    Season season;
    private int year;
    private int month;
    private int day;
    private int hour;

    public void nextHour(){
        hour += 1;
        if(hour > 22) {
            hour = 9;
            nextDay();
        }
    }

    public void nextDay(){
        day += 1;
        if(day > 28) {
            day = 1;
            nextMonth();
        }
    }

    public void nextMonth(){
        month += 1;
        if(month > 12) {
            month = 1;
            nextYear();
        }
        updateSeason();
    }

    public void nextYear(){
        year += 1;
    }

    public void updateSeason(){
        if(month >= 1 && month <= 3) {
            season = Season.SPRING;
        } else if(month >= 4 && month <= 6) {
            season = Season.SUMMER;
        } else if(month >= 7 && month <= 9) {
            season = Season.FALL;
        } else {
            season = Season.WINTER;
        }
    }

    public void setUpDateTime(){
        this.year = 1990;
        this.month = 1;
        this.season = Season.SPRING;
        this.day = 1;
        this.hour = 9;
        updateSeason();
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