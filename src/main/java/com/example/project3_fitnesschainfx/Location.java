package com.example.project3_fitnesschainfx;

/**
 * This enum is for the creation and retrieving of a Location enum constant.
 * Each location has a zipcode and also a county that can be retrieved and can be compared.
 *
 * @author Kangwei Zhu, Michael Israel
 */
public enum Location {
    BRIDGEWATER("08807", "Somerset"),
    EDISON("08837", "Middlesex"),
    FRANKLIN("08873", "Somerset"),
    PISCATAWAY("08854", "Middlesex"),
    SOMERVILLE("08876", "Somerset");

    private final String zipCode;

    /**
     * get the zipcode of one Location object
     *
     * @return The zipcode of one Location object
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * get the county name of one Location object
     *
     * @return The county name of one Location object
     */
    public String getCounty() {
        return county;
    }

    private final String county;

    /**
     * This is a constructor method of enum Location class that takes two parameters.
     * Automatically creates the enum constants above with the zipcode and county given
     *
     * @param zipCode the zipcode of location
     * @param county  the county of location
     */
    Location(String zipCode, String county) {
        this.zipCode = zipCode;
        this.county = county;
    }

    /**
     * This compareLocation Method compares the Locations of two members.
     * Checks and compares county's and if equal returns difference of their zipcodes
     *
     * @param location the object to be compared.
     * @return a number smaller than 0 if first county smaller than second or equal but firstZipCode smaller than secondZipCode, 0 if two locations are equal, a number greater than 0 if first county greater than second or equal but firstZipCode > secondZipCode
     */
    public int compareLocation(Location location) {
        String firstCounty = (this.getCounty()).toLowerCase();
        String secondCounty = (location.getCounty()).toLowerCase();
        int firstZipCode = Integer.parseInt(this.getZipCode());
        int secondZipCode = Integer.parseInt(location.getZipCode());
        if (firstCounty.compareTo(secondCounty) == 0) {
            if (firstZipCode > secondZipCode) {
                return 1;
            } else if (firstZipCode < secondZipCode) {
                return -1;
            } else {
                return 0;
            }
        }
        return firstCounty.compareTo(secondCounty);
    }
}
