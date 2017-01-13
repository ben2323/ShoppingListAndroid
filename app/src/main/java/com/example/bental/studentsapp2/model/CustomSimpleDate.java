package com.example.bental.studentsapp2.model;

/**
 * Created by ben on 12/17/2016.
 */

public class CustomSimpleDate {
    int year;
    int day;
    int month;
    int hour;
    int minute;
    int second;

    public CustomSimpleDate createDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        return this;
    }

    public CustomSimpleDate createTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        return this;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public String toDateString(){
       return day + "/" + month + "/" + year;
    }
    public String toTimeString(){
        return ( hour < 10 ? "0" : "" ) + hour + ":" + ( minute < 10 ? "0" : "" ) + minute;
    }
}
