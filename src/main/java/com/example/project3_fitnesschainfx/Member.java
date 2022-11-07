package com.example.project3_fitnesschainfx;

/**
 * The Member class is for the creation and data manipulation of Member object. It is the root of the membership hierarchy
 * In this project, the Member class is the super class of Family class and Premium class.
 * A Member object could be viewed as a real person in the world. He should have first name, last name, date of birth.
 * And if this person is registered to a fitness class, he also should have expiration date and the location of the
 * fitness class he takes.
 *
 * @author Michael Israel, Kangwei Zhu
 */
public class Member implements Comparable<Member> {
    private String fname;
    private String lname;
    private Date dob;
    private Date expire;
    private Location location;
    private final double ONE_TIME_FEE = 29.99;
    private final double MONTHLY_FEE = 39.99;
    private final int MONTH_NUM = 3;

    /**
     * This is a constructor method for member class that takes any parameters
     * This method should be used when creating a member object without any information of it.
     */
    public Member() {
    }

    /**
     * This is a constructor method of member class that takes five parameters.
     * This method is supposed to use when creating a member object with full information
     * set up all the information of this member.
     *
     * @param fname    The first name of this member object.
     * @param lname    The last name of this member object.
     * @param dob      The date of birth of this member object.
     * @param expire   This member object's fitness class expiration date.
     * @param location This member object's fitness gym's location.
     */
    public Member(String fname, String lname, Date dob, Date expire, Location location) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = expire;
        this.location = location;
    }

    /**
     * This is a constructor method of member class that takes three parameters.
     * This method should be used when a member object is created for check into database or register a class.
     *
     * @param fname The first name of this member.
     * @param lname The last name of this member.
     * @param dob   This member's date of birth.
     */
    public Member(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * This is a constructor method of member class takes two parameters.
     * This method should only be used in testbed main.
     *
     * @param fname The first name of this member.
     * @param lname The last name of this member.
     */
    public Member(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    /**
     * The method is used when getting the first name of a Member object.
     *
     * @return First name of a Member object.
     */
    public String getFname() {
        return fname;
    }

    /**
     * This method is used when getting the last name of a Member object.
     *
     * @return Last name of a Member object.
     */
    public String getLname() {
        return lname;
    }

    /**
     * This method is used when getting the date of birth of a Member object.
     *
     * @return The Date type date of birth.
     */
    public Date getDob() {
        return dob;
    }

    /**
     * This method is used when getting the expiration date of a Member object.
     *
     * @return The Date type expiration date.
     */
    public Date getExpire() {
        return expire;
    }

    /**
     * This method is used when getting the fitness class location of a Member object.
     *
     * @return The location of this Member object
     */
    public Location getLocation() {
        return location;
    }

    /**
     * This method overrides the original Member toString() method.
     * Now it shows all the information of a Member object
     *
     * @return Full information include first name, last name, date of birth, zipcode, and county of a Member object.
     */
    @Override
    public String toString() {
        return this.fname + " " + this.lname + ", DOB: " + this.dob.toString() + ", Membership expires "
                + this.expire.toString() + ", Location: " + this.location + ", "
                + this.location.getZipCode() + ", " + this.location.getCounty().toUpperCase();
    }

    /**
     * This equal method checks whether two members are same.
     * This method will check if the input object is an instance of Member, if it is, then compared the member with this
     * input member, and check whether they are the same. At here two members that have the same first name, last name,
     * and date of birth are considered as equal.
     *
     * @param obj input object
     * @return true if they are same, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Member objMember) {
            boolean isFNameSame = this.fname.equalsIgnoreCase(objMember.fname);
            boolean isLNameSame = this.lname.equalsIgnoreCase(objMember.lname);
            boolean isDobSame = this.getDob().getDay() == objMember.getDob().getDay() &&
                    this.getDob().getMonth() == objMember.getDob().getMonth() &&
                    this.getDob().getYear() == objMember.getDob().getYear();
            return isFNameSame && isLNameSame && isDobSame;
        }
        return false;
    }

    /**
     * This method is used when getting the membership Fee of member Type membership
     *
     * @return The membership fee.
     */
    public double membershipFee() {
        return ONE_TIME_FEE + MONTHLY_FEE * MONTH_NUM;
    }

    /**
     * This method compares the two member's names' value(ignore case difference). It is used when sorting an array of
     * Member object by name.
     * This method calls java.lang.String compareTo() method when comparing the members' names. String's compareTo
     * method is comparing two strings lexicographically.The comparison is based on the Unicode value of each character
     * in the strings.
     *
     * @param member the object to be compared.
     * @return 1 if first member greater than second, 0 if two member are equal, -1 if first member smaller than second/
     */
    @Override
    public int compareTo(Member member) {
        String first = (this.lname + this.fname).toLowerCase();
        String second = (member.lname + member.fname).toLowerCase();
        if (first.compareTo(second) > 0) {
            return 1;
        } else if (first.compareTo(second) == 0) {
            return 0;
        } else {
            return -1;
        }
    }
}
