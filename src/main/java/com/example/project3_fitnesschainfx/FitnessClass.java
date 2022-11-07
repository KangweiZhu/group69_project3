package com.example.project3_fitnesschainfx;

import java.util.ArrayList;

/**
 * FitnessClass is the class that defines a fitness class the member can check in.
 *
 * @author Michael Israel, Kangwei Zhu
 */
public class FitnessClass {
    private final int NOTFOUND = -1;
    private String instructorName;
    private String fitnessClassName;
    private Time classTime;
    private final MemberDatabase studentsList = new MemberDatabase();
    private ArrayList<Member> guestList = new ArrayList<>();
    private Location location;
    private ArrayList<String> fitnessClassInfos = new ArrayList<>();

    /**
     * Construct a FitnessClass object. Default constructor.
     */
    public FitnessClass() {
    }

    /**
     * Constructor with parameters. Gives value to the instance variable of FitnessClass object .
     *
     * @param fitnessClassName The name of the fitnessClass.
     * @param instructorName   The name of the instructor of that fitness class
     * @param classTime        The time that the fitnessclass open
     * @param location         The location of that fitness class.
     */
    public FitnessClass(String fitnessClassName, String instructorName, Time classTime, Location location) {
        this.fitnessClassName = fitnessClassName;
        this.instructorName = instructorName;
        this.classTime = classTime;
        this.location = location;
    }

    /**
     * Get the name of this fitness class instructor.
     *
     * @return The name of this fitness class instructor.
     */
    public String getInstructor() {
        return instructorName;
    }

    /**
     * Get the name of this fitness class.
     *
     * @return The name of this fitness class.
     */
    public String getFitnessClassName() {
        return fitnessClassName;
    }

    /**
     * Get the time when this fitness class begins.
     *
     * @return The time when this fitness class begins.
     */
    public Time getTime() {
        return classTime;
    }

    /**
     * Print the detail of the fitness class.
     * Notice that it would print the fitness class name, instructor name, time that class begins and all the members in
     * this fitness class(if exists).
     */
    public void printSchedule() {
        if (studentsList.getSize() != 0) {
            fitnessClassInfos.add("- Participants -");
            printDetail();
        }
        if (guestList.size() != 0) {
            fitnessClassInfos.add("- Guests -");
            for (int i = 0; i < guestList.size(); i++) {
                fitnessClassInfos.add("  ");
                Member curMember = guestList.get(i);
                if (curMember instanceof Premium) {
                    fitnessClassInfos.add(curMember.toString() + ", (Premium) guest-pass remaining: " +
                            ((Premium) curMember).getNumOfGuestPass());
                } else if (curMember instanceof Family) {
                    fitnessClassInfos.add(curMember.toString() + ", (Family) Guest-pass remaining: "
                            + ((Family) curMember).getNumOfGuestPass());
                } else {
                    fitnessClassInfos.add(curMember.toString());
                }
            }
        }
    }

    /**
     * Print all the Member objects that are currently stored in database. Notice that this method is used for printing
     * the all the members in one specific fitness database.
     */
    public void printDetail() {
        for (int i = 0; i < studentsList.getSize(); i++) {
            String curInfo = "  ";
            curInfo += studentsList.getMlist()[i].toString();
            fitnessClassInfos.add(curInfo);
        }
    }
    /**
     * Display the whole info of this Fitness Class and its schedule.
     */
    public void printInfo() {
        fitnessClassInfos.clear();
        fitnessClassInfos.add(this.toString());
        printSchedule();
    }

    /**
     * Get the location of this fitnessClass.
     *
     * @return Location object of this fitnessClass.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Check whether a member is checked in this fitness class or not.
     *
     * @param member A specific Member object that are going to be checked whether it has checked in this course or not.
     * @return True if it is checked in, otherwise false.
     */
    public boolean isRegistered(Member member) {
        return studentsList.contains(member) != NOTFOUND;
    }

    /**
     * Check whether a member's membership has expired or not.
     *
     * @param member A specific Member object that going to be check if his membership has expired.
     * @return True if it is expired, false otherwise.
     */
    public boolean isExpired(Member member) {
        return member.getExpire().compareTo(new Date()) < 0;
    }

    /**
     * Check in a member to this class's student list.
     *
     * @param member The Member object you want to check in.
     * @return true if add successfully, otherwise false.
     */
    public boolean addMember(Member member) {
        return studentsList.add(member);
    }

    /**
     * Check in a guest to this class's guest list.
     *
     * @param member The Member object that will have a guest
     * @return true if add successfully, otherwise false.
     */
    public boolean addGuest(Member member) {
        return guestList.add(member);
    }

    public ArrayList<String> getFitnessClassInfos(){
        return fitnessClassInfos;
    }
    /**
     * Drop a member from this fitness class's student list.
     *
     * @param member The member you want to drop.
     * @return True if successfully dropped, otherwise false.
     */
    public boolean drop(Member member) {
        boolean flag = false;
        if (studentsList.contains(member) >= 0) {
            flag = studentsList.remove(member);
        }
        return flag;
    }

    /**
     * Drop a Member object's guest from this fitness class's guestList
     *
     * @param member The member that bring this guest.
     * @return True if successfully dropped, false otherwise.
     */
    public boolean dropGuest(Member member) {
        boolean flag = guestList.remove(member);
        if (member instanceof Family) {
            ((Family) member).setNumOfGuestPass(1);
        }
        return flag;
    }

    /**
     * This method is used when stringed version of fitnessClass object
     *
     * @return a String that print the information of fitnessClass object
     */
    @Override
    public String toString() {
        return fitnessClassName.toUpperCase() + " - " + instructorName.toUpperCase() + ", " + classTime.getDateTime() +
                ", " + location;
    }

    /**
     * This equal method checks whether two FitnessClass object are the same
     * To be the same, they should have the same Location, same Instructor, same className.
     *
     * @param obj An input Object
     * @return True if they are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FitnessClass fitnessClass) {
            if (fitnessClass.getLocation().compareLocation(location) == 0 && fitnessClass.getInstructor().
                    equalsIgnoreCase(instructorName) && fitnessClass.getFitnessClassName().
                    equalsIgnoreCase(fitnessClassName)) {
                return true;
            }
        }
        return false;
    }
}