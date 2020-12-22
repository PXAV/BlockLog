package de.pxav.blocklog.model.serial;

import java.io.Serializable;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class SerialTime implements Serializable {

  private int second;
  private int minute;
  private int hour;
  private int dayOfMonth;
  private int month;
  private int year;

  public SerialTime(int second, int minute, int hour, int dayOfMonth, int month, int year) {
    this.second = second;
    this.minute = minute;
    this.hour = hour;
    this.dayOfMonth = dayOfMonth;
    this.month = month;
    this.year = year;
  }

  public SerialTime() {}

  public int getSecond() {
    return second;
  }

  public void setSecond(int second) {
    this.second = second;
  }

  public int getMinute() {
    return minute;
  }

  public void setMinute(int minute) {
    this.minute = minute;
  }

  public int getHour() {
    return hour;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }

  public int getDayOfMonth() {
    return dayOfMonth;
  }

  public void setDayOfMonth(int dayOfMonth) {
    this.dayOfMonth = dayOfMonth;
  }

  public int getMonthOfYear() {
    return month;
  }

  public void setMonthOfYear(int monthOfYear) {
    this.month = monthOfYear;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

}
