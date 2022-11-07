package com.example.project3_fitnesschainfx;

/**
 * This class is the class that extends the Member class. It is also the super class of The Premium Class.
 * This class include specific data and operation to a family type membership in this fitness chain.
 *
 * @author Michael Israel, Kangwei Zhu
 */
public class Family extends Member {
    protected int numOfGuestPass;
    private final double ONE_TIME_FEE = 29.99;
    private final double MONTHLY_FEE = 59.99;
    private final int MONTH_NUM = 3;

    /**
     * Constructor without parameters.
     * Initialize the number of guest pass to 1.
     */
    public Family() {
        numOfGuestPass = 1;
    }

    /**
     * Constructor with parameters
     * Setting all the attributes of Family type membership by calling the super class's constructor method
     *
     * @param firstName      The first name of this member object.
     * @param lastName       The last name of this member object.
     * @param dob            The date of birth of this member object.
     * @param expirationDate This member object's fitness class expiration date.
     * @param location       This member object's fitness gym's location.
     */
    public Family(String firstName, String lastName, Date dob, Date expirationDate, Location location) {
        super(firstName, lastName, dob, expirationDate, location);
        this.numOfGuestPass = 1;
    }

    /**
     * Get the number of guest pass this Family object currently has
     *
     * @return the number of guest pass this Family object currently has
     */
    public int getNumOfGuestPass() {
        return numOfGuestPass;
    }

    /**
     * Increase the current number of guest pass that this Family object has with a specific value
     *
     * @param num The specific value.
     */
    public void setNumOfGuestPass(int num) {
        this.numOfGuestPass += num;
    }

    /**
     * This method is used when getting the membership Fee that Family membership requires to paid quarterly
     *
     * @return the membership Fee that Family typed membership need to pay.
     */
    @Override
    public double membershipFee() {
        return ONE_TIME_FEE + MONTHLY_FEE * MONTH_NUM;
    }
}
