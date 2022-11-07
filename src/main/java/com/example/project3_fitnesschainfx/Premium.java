package com.example.project3_fitnesschainfx;

/**
 * This class extends from the Family class.
 * <p>
 * It is used for representing the Premium membership of a fitness chain in real life.
 *
 * @author Michael Israel, Kangwei Zhu
 */
public class Premium extends Family {
    private final double MONTHLY_FEE = 59.99;
    private final double MONTH_NUM = 11;

    /**
     * Default constructor. Initialize the number of guest pass to three.
     */
    public Premium() {
        this.numOfGuestPass = 3;
    }

    /**
     * Parameterized constructor. Setting up the attributes(instance variables) by calling the constructor method from
     * super class.
     *
     * @param firstName  The first name of this member object.
     * @param lastName   The last name of this member object.
     * @param dob        The date of birth of this member object.
     * @param expireDate This member object's fitness class expiration date.
     * @param location   This member object's fitness gym's location.
     */
    public Premium(String firstName, String lastName, Date dob, Date expireDate, Location location) {
        super(firstName, lastName, dob, expireDate, location);
        this.numOfGuestPass = 3;
    }

    /**
     * This method is used when getting the membership Fee that Family membership requires to paid quarterly
     *
     * @return the membership Fee that Family typed membership need to pay.
     */
    @Override
    public double membershipFee() {
        return MONTHLY_FEE * MONTH_NUM;
    }
}
