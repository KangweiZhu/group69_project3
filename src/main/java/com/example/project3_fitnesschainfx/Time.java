package com.example.project3_fitnesschainfx;

/**
 * This enum class is for the creation and retrieving of a Time enum constant.
 * Each constant has a dateTime which can be retrieved
 *
 * @author Kangwei Zhu, Michael Israel
 */
public enum Time {
    MORNING("9:30"),
    AFTERNOON("14:00"),
    EVENING("18:30");

    private final String dateTime;

    /**
     * This is a constructor method of enum Time class that takes one parameter.
     * Automatically creates the enum constants above with the times given for them
     *
     * @param dateTime the time given class starts
     */
    Time(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * The method is used when getting the time of a fitness class.
     *
     * @return the time of the desired class in String format
     */
    public String getDateTime() {
        return dateTime;
    }

}
