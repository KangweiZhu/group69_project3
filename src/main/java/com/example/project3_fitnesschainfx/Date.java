package com.example.project3_fitnesschainfx;

import java.util.Calendar;
import java.util.StringTokenizer;


/**
 * This class is for the creation and manipulation of Date object.
 * Date object holds information such as year, month, and day.
 * Can also be created as the mm/dd/yyyy of the current day (i.e. today)
 *
 * @author Michael Israel, Kangwei Zhu
 */
public class Date implements Comparable<Date> {
    public static final int FIRSTDAYOFMONTH = 0;
    public static final int ZERO = 0;
    public static final int MONTHSINYEAR = 12;
    public static final int BIGMONTHS = 31;
    public static final int MEDIUMMONTHS = 30;
    public static final int FEBLEAPYEAR = 29;
    public static final int LITTLEMONTHS = 28;
    public static final int VALIDAGE = 18;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    private static final int SMALLER = -1;
    private static final int EQUAL = 0;
    private static final int GREATER = 1;
    private static final int January = 1;
    private static final int February = 2;
    private static final int March = 3;
    private static final int April = 4;
    private static final int May = 5;
    private static final int June = 6;
    private static final int July = 7;
    private static final int August = 8;
    private static final int September = 9;
    private static final int October = 10;
    private static final int November = 11;
    private static final int December = 12;
    private static final int ISPROBABLYDEAD = 1900;
    private final int year;
    private final int month;
    private final int day;

    /**
     * This is a constructor method for Date class that creates an object with today’s date
     * This method should be used when you need to know the current calendar date
     */
    public Date() {
        Calendar todaysDate = Calendar.getInstance();
        this.day = todaysDate.get(Calendar.DATE);
        this.month = todaysDate.get(Calendar.MONTH) + 1;
        this.year = todaysDate.get(Calendar.YEAR);
    }

    /**
     * This is a constructor method for Date class that takes “mm/dd/yyyy” and creates a Date object
     * This method should be used when you want to keep track of dob or expirations
     *
     * @param date The date in mm/dd/yyyy String format
     */
    public Date(String date) {
        StringTokenizer st = new StringTokenizer(date, "/");
        this.month = Integer.parseInt(st.nextToken());
        this.day = Integer.parseInt(st.nextToken());
        this.year = Integer.parseInt(st.nextToken());
    }

    /**
     * The method is used when getting the year of a Date.
     *
     * @return the year of the desired Date
     */
    public int getYear() {
        return year;
    }

    /**
     * The method is used when getting the month of a Date.
     *
     * @return the month of the desired Date
     */
    public int getMonth() {
        return month;
    }

    /**
     * The method is used when getting the day of a Date.
     *
     * @return the day of the desired Date
     */
    public int getDay() {
        return day;
    }

    /**
     * This method compares the two date values(ignore case difference).
     * Checks and compares the individual calendar year, month, and day
     *
     * @param date the date to be compared.
     * @return 1 if first date greater than second, 0 if two dates are equal, -1 if first date smaller than second/
     */
    @Override
    public int compareTo(Date date) {
        if (this.year > date.year) {
            return GREATER;
        } else if (this.year < date.year) {
            return SMALLER;
        } else {
            if (this.month > date.month) {
                return GREATER;
            } else if (this.month < date.month) {
                return SMALLER;
            } else {
                if (this.day > date.day) {
                    return GREATER;
                } else if (this.day < date.day) {
                    return SMALLER;
                } else {
                    return EQUAL;
                }
            }
        }
    }

    /**
     * This method checks if a year is a leap year.
     *
     * @return true if date is a leap year, false otherwise
     */
    public boolean isLeapYear() {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                return year % QUATERCENTENNIAL == 0;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if a date of expiration is valid.
     * Checks if date is a valid calendar date
     *
     * @return true if date given is a valid date, false otherwise
     */
    public boolean isValidExpiration() {
        return this.isValid();
    }

    /**
     * Check whether the year comes to next year if the date is increased by several month.
     *
     * @param addMonth The number of month that added to the Date object
     * @return the month in next year if it exceeds the month limit of current year. otherwise -1
     */
    public int checkNextYear(int addMonth) {
        if (this.month + addMonth > MONTHSINYEAR) {
            return this.month + addMonth - MONTHSINYEAR;
        } else {
            return -1;
        }
    }

    /**
     * This method checks if a date is valid calendar date.
     * checks if the year is greater or equal than 0 and smaller or equal to currentYear, the month is greater or equal
     * to 0 and smaller or equal to 12, the day is greater or equal to 0 and (depends on month and if it's a leap year
     * or not)
     *
     * @return true if date given is a valid date, false otherwise
     */
    public boolean isValid() {
        if (year > ISPROBABLYDEAD && month <= MONTHSINYEAR && month > 0) {
            if (month == January || month == March || month == May || month == July || month == August || month == December || month == October) {
                return (day > ZERO && day <= BIGMONTHS);
            } else if (month == April || month == June || month == September || month == November) {
                return (day > ZERO && day <= MEDIUMMONTHS);
            } else if (month == February) {
                if (isLeapYear()) {
                    return (day > ZERO && day <= FEBLEAPYEAR);
                } else {
                    return (day > ZERO && day <= LITTLEMONTHS);
                }
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * The method is used when stringed version of a Date.
     *
     * @return the date in “mm/dd/yyyy” String format
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }
}
