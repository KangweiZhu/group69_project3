package com.example.project3_fitnesschainfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

import static com.example.project3_fitnesschainfx.Date.VALIDAGE;

/**
 * This class is for the running of a Gym.
 * Given commands can either add, delete, or print a list of members
 * Given commands can also add, delete, or print a list of classes for members
 *
 * @author Kangwei Zhu, Michael Israel
 */
public class GymManagerController {
    protected static final int INDEX_OF_CLASS_NAME = 0;
    protected static final int INDEX_OF_INSTRUCTOR = 1;
    protected static final int INDEX_OF_FIRSTNAME = 1;
    protected static final int INDEX_OF_LASTNAME = 2;
    protected static final int INDEX_OF_DAYTIME = 2;
    protected static final int INDEX_OF_DOB = 3;
    protected static final int INDEX_OF_LOCATION = 3;
    protected static final int MEMBER_AND_FAMILY_EXPIRE = 3;
    protected static final int INDEX_OF_EXPIRATION_DATE = 4;

    MemberDatabase memberDB = new MemberDatabase();
    ClassSchedule classSchedule = new ClassSchedule();

    @FXML
    private TextField fnameTextField;
    @FXML
    private TextField lnameTextField;
    @FXML
    private TextField dobTextField;
    @FXML
    private TextField locationTextField;
    @FXML
    private RadioButton standard;
    @FXML
    private RadioButton family;
    @FXML
    private RadioButton premium;
    @FXML
    private TextArea outputText;
    @FXML
    private TextField className;
    @FXML
    private TextField instructorName;
    @FXML
    private TextField memberFname;
    @FXML
    private TextField memberLname;
    @FXML
    private TextField dobFit;
    @FXML
    private TextField loc;
    @FXML
    private RadioButton memberCheckIn;
    @FXML
    private RadioButton memberDrop;
    @FXML
    private RadioButton guestCheckIn;
    @FXML
    private RadioButton guestDrop;

    /**
     * Default constructor.
     */
    public GymManagerController() {
    }

    /**
     * The method is used when adding a member to the database when add button is clicked.
     *
     * @param event An Event representing some type of action.
     */
    @FXML
    void clickAdd(ActionEvent event) {
        String fname = fnameTextField.getText();
        String lname = lnameTextField.getText();
        String stringDob = dobTextField.getText();
        String location = locationTextField.getText();
        displayErrorMsg(fname, "fname");
        displayErrorMsg(lname, "lname");
        displayErrorMsg(stringDob, "stringDob");
        displayErrorMsg(location, "location");
        int addType;
        if (standard.isSelected()) {
            addType = 0;
        } else if (family.isSelected()) {
            addType = 1;
        } else if (premium.isSelected()) {
            addType = -1;
        } else {
            outputText.appendText("Please select something!\n");
            return;
        }
        Date dob = new Date(stringDob);
        Member newMember = new Member(fname, lname, dob);
        doCheckIn(newMember, location, addType, outputText);
    }

    /**
     * The method is used to load the fitness class schedule from the file classSchedule.txt to the class
     * schedule in the software system.
     *
     * @param event An Event representing some type of action.
     */
    @FXML
    void loadClassScheduleList(ActionEvent event) {
        File textClassSchedule = importFile("classSchedule");
        if (textClassSchedule != null) {
            outputText.appendText("The absolute path of the file you choose is: "
                    + textClassSchedule.getAbsolutePath() + "\n");
            classSchedule.LS(textClassSchedule);
            displayInfo(classSchedule.getWarning(), outputText);
        } else {
            outputText.appendText("Please select the classSchedule text file in order to load the class Schedule\n");
        }
    }

    /**
     * The method is used when removing a member from the database when remove button is clicked.
     */
    @FXML
    void clickRemove() {
        if (memberDB.getSize() == 0) {
            outputText.appendText("Member Database is empty!\n");
        } else {
            String fname = fnameTextField.getText();
            String lname = lnameTextField.getText();
            String stringDob = dobTextField.getText();
            Date date = new Date(stringDob);
            Member member = new Member(fname, lname, date);
            if (memberDB.remove(member)) {
                outputText.appendText(member.getFname() + " " + member.getLname() + " removed.\n");
            } else {
                outputText.appendText(member.getFname() + " " + member.getLname() + " is not in the database.\n");
            }
        }
    }

    /**
     * The method is used to keep track of info based on check-ins and drops:
     * The method checks members into a fitness class.
     * The method checks members out of a fitness class if they have completed the class.
     * The method is used to keep track of the remaining number of guest passes an individual has left.
     * - Checked whenever a family guest checks in for a fitness class.
     * The method is used to keep track of the remaining number of guest passes an individual has left.
     * - Checked whenever a guest is done with a fitness class, and checking out.
     *
     * @param event An Event representing some type of action.
     */
    @FXML
    void fitnessClassTransactions(ActionEvent event) {
        String fName = memberFname.getText();
        String lName = memberLname.getText();
        String fitnessClassName = className.getText();
        String newlocation = loc.getText();
        Location location;
        Date dob = new Date(dobFit.getText());
        if (!isValidDob(dob, outputText)) {
            return;
        }
        String instructor = instructorName.getText();
        Member newMember = new Member(fName, lName, dob);
        if (memberDB.contains(newMember) >= 0) {
            newMember = memberDB.getMember(newMember);
        } else {
            outputText.appendText(fName + " " + lName + " " + dob + " is not in the database." + "\n");
            return;
        }
        if (isValidLocation(newlocation)) {
            location = Location.valueOf(newlocation.toUpperCase());
        } else {
            return;
        }
        FitnessClass fitnessClass = new FitnessClass(fitnessClassName, instructor, null, location);
        if (classSchedule.isFitnessClassExist(fitnessClass)) {
            fitnessClass = classSchedule.getFitnessClass(fitnessClass);
            if (fitnessClass == null) {
                return;
            }
            if (memberCheckIn.isSelected()) {
                doMemberCheckIn(fitnessClass, newMember);
            } else if (memberDrop.isSelected()) {
                doDrop(fitnessClass, newMember);
            } else if (guestCheckIn.isSelected()) {
                doDG(fitnessClass, newMember);
            } else if (guestDrop.isSelected()) {
                doCG(fitnessClass, newMember);
            }
        } else {
            displayInfo(classSchedule.getWarning(), outputText);
        }
    }

    /**
     * The method is used to display the fitness class schedule.
     * A fitness class shall include the fitness class name, instructor’s name, the time of the class,
     * and the list of members who have already checked in today
     *
     * @param event An Event representing some type of action.
     */
    @FXML
    void viewClassSchedule(ActionEvent event) {
        ArrayList<String> infos = new ArrayList<>();
        if (classSchedule.getNumClasses() == 0) {
            outputText.appendText("Fitness class schedule is empty.\n");
        } else {
            for (int i = 0; i < classSchedule.getNumClasses(); i++) {
                classSchedule.getFitnessClasses()[i].printInfo();
                displayInfo(classSchedule.getFitnessClasses()[i].getFitnessClassInfos(), outputText);
            }
        }
    }

    /**
     * The method is used to load a list of members from the file memberList.txt to the member database.
     *
     * @param event An Event representing some type of action.
     */
    @FXML
    void loadMemberList(ActionEvent event) {
        File memberList = importFile("memberList");
        if (memberList != null) {
            outputText.appendText("The absolute path of the file you choose is: " + memberList.getAbsolutePath()
                    + "\n");
            memberDB.LM(memberList);
            displayInfo(memberDB.getDbWarning(), outputText);
        } else {
            outputText.appendText("Please select something in order to load memberList\n");
        }
    }

    /**
     * Print all the Member objects that are currently stored in database. Notice that this method is used for printing
     * the all the members in one specific fitness database.
     *
     * @param event An Event representing some type of action.
     */
    @FXML
    void defaultPrint(ActionEvent event) {
        if (memberDB.getSize() != 0) {
            outputText.appendText("-list of members-\n");
        }
        memberDB.getDbWarning().clear();
        memberDB.print();
        displayInfo(memberDB.getDbWarning(), outputText);
    }

    /**
     * Sort the database Member objects by its first name and last name  and then display order.
     * First check first name, then check last name.
     *
     * @param event An Event representing some type of action.
     */
    @FXML
    void viewByName(ActionEvent event) {
        memberDB.printByName();
        displayInfo(memberDB.getDbWarning(), outputText);
    }

    /**
     * Sort the database Member objects by county name, if county name is equal, then sort by zipcode and
     * then display order.
     *
     * @param event An Event representing some type of action.
     */
    @FXML
    void viewByCounty(ActionEvent event) {
        memberDB.printByCounty();
        displayInfo(memberDB.getDbWarning(), outputText);
    }

    /**
     * Sort the database Member objects by its expiration date and then display order.
     *
     * @param event An Event representing some type of action.
     */
    @FXML
    void viewByExpirationDate(ActionEvent event) {
        memberDB.printByExpirationDate();
        displayInfo(memberDB.getDbWarning(), outputText);
    }

    /**
     * Sort the database Member objects by their membership fees and then display them.
     *
     * @param event An Event representing some type of action.
     */
    @FXML
    void viewByMembershipFees(ActionEvent event) {
        memberDB.printByMembershipFees();
        displayInfo(memberDB.getDbWarning(), outputText);
    }

    /**
     * This method is used when you click the import button, it will pop up a new window and enable you to select the
     * file you want
     *
     * @param fileName The name of file. At here we only suppose to have memberList and classSchedule
     * @return source file
     */
    private File importFile(String fileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose " + fileName + " from your file system");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = fileChooser.showOpenDialog(stage);
        return sourceFile;
    }

    /**
     * This method is called when dropping a specific member from a specific fitnessClass.
     *
     * @param fitnessClass The fitness class that the member is going to be removed.
     * @param member       The member that are going to be removed.
     * @return True if successfully dropped, false otherwise.
     */
    private boolean doDrop(FitnessClass fitnessClass, Member member) {
        boolean flag = fitnessClass.drop(member);
        if (flag) {
            outputText.appendText(member.getFname() + " " + member.getLname() + " done with the class.\n");
        } else {
            outputText.appendText(member.getFname() + " " + member.getLname() + " did not check in.\n");
        }
        return flag;
    }

    /**
     * The method is used to keep track of the remaining number of guest passes an individual has left.
     * Checked whenever a family guest checks in for a fitness class.
     *
     * @param fitnessClass Takes in fitness class individuals trying to get into
     * @param member       Takes in member object that wants to check into a fitness class
     */
    private boolean doCG(FitnessClass fitnessClass, Member member) {
        int numOfPass = 0;
        boolean flag = false;
        if (member instanceof Family) {
            numOfPass = ((Family) member).getNumOfGuestPass();
            if (numOfPass == 0) {
                outputText.appendText(member.getFname() + " " + member.getLname() + " ran out of guest " +
                        "pass.\n");
            } else {
                if (member.getLocation().compareLocation(fitnessClass.getLocation()) == 0) {
                    ((Family) member).setNumOfGuestPass(-1);
                    flag = fitnessClass.addGuest(member);
                    outputText.appendText(member.getFname() + " " + member.getLname() + " (guest) checked in "
                            + fitnessClass.toString() + "\n");
                    fitnessClass.getFitnessClassInfos().clear();
                    fitnessClass.printSchedule();
                    displayInfo(fitnessClass.getFitnessClassInfos(), outputText);
                } else {
                    Location location = fitnessClass.getLocation();
                    String zipCode = location.getZipCode();
                    String county = location.getCounty().toUpperCase();
                    outputText.appendText(member.getFname() + " " + member.getLname() + " Guest checking in "
                            + location + ", " + zipCode + ", " + county + " - guest location restriction.\n");
                }
            }
        } else {
            outputText.appendText("Standard membership - guest check-in is not allowed.\n");
        }
        return flag;
    }

    /**
     * This method will check in a member into the memberDB
     *
     * @param newMember The Member object that will be checked in
     * @param location  The register location of that member going to be registered
     * @param addType   0 : Member Object
     *                  1: Family Object which extends Member Object
     *                  -1: Premium Object which extends Family Object
     */
    private void doCheckIn(Member newMember, String location, int addType, TextArea outputText) {
        String firstName = newMember.getFname();
        String lastName = newMember.getLname();
        Date dob = newMember.getDob();
        Location regisLocation = null;
        if (isValidLocation(location)) {
            regisLocation = Location.valueOf(location.toUpperCase());
        } else {
            return;
        }
        Date curDate = new Date();
        Date expireDate;
        if (addType == 0 || addType == 1) {
            if (curDate.checkNextYear(MEMBER_AND_FAMILY_EXPIRE) >= 0) {
                expireDate = new Date(curDate.checkNextYear(MEMBER_AND_FAMILY_EXPIRE) + "/" + curDate.getDay() + "/" +
                        (curDate.getYear() + 1));
            } else {
                expireDate = new Date(curDate.getMonth() + MEMBER_AND_FAMILY_EXPIRE + "/" + curDate.getDay() + "/" +
                        curDate.getYear());
            }
            if (addType == 0) {
                newMember = new Member(firstName, lastName, dob, expireDate, regisLocation);
            } else {
                newMember = new Family(firstName, lastName, dob, expireDate, regisLocation);
            }
        } else {
            expireDate = new Date(curDate.getMonth() + "/" + curDate.getDay() + "/" + (curDate.getYear() + 1));
            newMember = new Premium(firstName, lastName, dob, expireDate, regisLocation);
        }
        if (checkDB(newMember, outputText)) {
            memberDB.add(newMember);
            outputText.appendText(newMember.getFname() + " " + newMember.getLname() + " added.\n");
        }
    }

    /**
     * check whether a member is already in database or not
     *
     * @param member The member that are going to be checked.
     * @return True if not exist, otherwise false.
     */
    private boolean checkDB(Member member, TextArea outputText) {
        if (memberDB.contains(member) < 0) {
            if (isValidDob(member.getDob(), outputText)) {
                if (member.getExpire().isValidExpiration()) {
                    return true;
                } else {
                    outputText.appendText("Expiration date " + this.toString() + ": invalid calendar date!\n");
                }
            }
        } else {
            outputText.appendText(member.getFname() + " " + member.getLname() + " is already in the database.\n");
        }
        return false;
    }

    /**
     * This method is used when dropping a member's guest to a specific class
     *
     * @param fitnessClass The specific fitness class that the guest is going to be dropped.
     * @param guest        The guest
     * @return True if successfully dropped, false otherwise.
     */
    private boolean doDG(FitnessClass fitnessClass, Member guest) {
        boolean flag = fitnessClass.dropGuest(guest);
        if (flag) {
            outputText.appendText(guest.getFname() + " " + guest.getLname() + " Guest done with the class.\n");
        }
        return flag;
    }


    /**
     * The method is used to see if a fitness class located at a location.
     *
     * @param loc The location of a fitness class
     * @return true if there is a fitness class located at the location. false otherwise
     */
    private boolean isValidLocation(String loc) {
        for (Location location : Location.values()) {
            if (location.toString().equalsIgnoreCase(loc)) {
                return true;
            }
        }
        outputText.appendText(loc + ": invalid location!\n");
        return false;
    }

    /**
     * This method checks if a date of birth is valid.
     * Checks if older than 18, is a valid calendar date, not today or a future date
     *
     * @return true if date given is a valid dob, false otherwise
     */
    public boolean isValidDob(Date date, TextArea outputText) {
        Date currentDate = new Date();
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        int currentDateyear = currentDate.getYear();
        int currentDatemonth = currentDate.getMonth();
        int currentDateday = currentDate.getDay();
        if (!date.isValid()) {
            outputText.appendText("DOB " + date.toString() + ": invalid calendar date!\n");
            return false;
        }
        if (((year < (currentDateyear - VALIDAGE)) ||
                (year == (currentDateyear - VALIDAGE) && month < currentDatemonth) ||
                (year == (currentDateyear - VALIDAGE) && month == currentDatemonth) && day <= currentDateday)) {
            return true;
        } else if ((year == currentDateyear && month == currentDatemonth && day == currentDateday) ||
                (year == currentDateyear && month > currentDatemonth) ||
                (year == currentDateyear && month == currentDatemonth && day >= currentDateday) ||
                (year > currentDateyear)) {
            outputText.appendText("DOB " + date.toString() + ": cannot be today or a future date!\n");
        } else {
            outputText.appendText("DOB " + date.toString() + ": must be 18 or older to join!\n");
        }
        return false;
    }

    /**
     * This is the method that will print error message.
     * It is called when the input does not meet the requirement.
     *
     * @param empty empty String.
     * @param info  The place where the error happens.
     */
    private void displayErrorMsg(String empty, String info) {
        if ("".equals(empty)) {
            outputText.appendText("You forgot to add " + info + ".\n");
        }
    }

    /**
     * The method is to append information from a given ArrayList to the desired textArea
     *
     * @param alst     Arraylist holing information
     * @param textArea Area to paste/print information being held
     */
    private void displayInfo(ArrayList<String> alst, TextArea textArea) {
        for (int i = 0; i < alst.size(); i++) {
            textArea.appendText(alst.get(i) + "\n");
        }
        textArea.appendText("\n");
    }

    /**
     * This method is used when check in a member into a fitnessClass. It also does the checking before add the member into
     * the fitnessClass's student list.
     *
     * @param fitnessClass The fitness class this member wants to check in
     * @param member       The member that are going to be checked in
     * @return True if successfully checked in, false otherwise.
     */
    private boolean doMemberCheckIn(FitnessClass fitnessClass, Member member) {
        String fName = member.getFname();
        String lName = member.getLname();
        Location location = fitnessClass.getLocation();
        String zipCode = location.getZipCode();
        String county = location.getCounty().toUpperCase();
        boolean flag = false;
        if (!fitnessClass.isRegistered(member)) {
            if (!fitnessClass.isExpired(member)) {
                if (!isTimeConflict(fitnessClass, member)) {
                    if (member instanceof Family) {
                        fitnessClass.addMember(member);
                        outputText.appendText(fName + " " + lName + " checked in " +
                                fitnessClass.toString() + "\n");
                        fitnessClass.getFitnessClassInfos().clear();
                        fitnessClass.printSchedule();
                        displayInfo(fitnessClass.getFitnessClassInfos(), outputText);
                    } else {
                        if (fitnessClass.getLocation().compareLocation(member.getLocation()) == 0) {
                            flag = fitnessClass.addMember(member);
                            outputText.appendText(fName + " " + lName + " checked in " +
                                    fitnessClass.toString() + "\n");
                            fitnessClass.getFitnessClassInfos().clear();
                            fitnessClass.printSchedule();
                            displayInfo(fitnessClass.getFitnessClassInfos(), outputText);
                        } else {
                            outputText.appendText(fName + " " + lName + " checking in " +
                                    location + ", " + zipCode + ", " + county +
                                    " - standard membership location restriction." + "\n");
                        }
                    }
                } else {
                    outputText.appendText("Time conflict - " + fitnessClass.toString() + ", " + zipCode + ", "
                            + county + "\n");
                }
            } else {
                outputText.appendText(fName + " " + lName + " " + member.getDob() +
                        " membership expired." + "\n");
            }
        } else {
            outputText.appendText(fName + " " + lName + " already checked in." + "\n");
        }
        return flag;
    }

    /**
     * Check if there is time conflict when a member want to check in a fitnessClass.
     * That is, if the member has already registered a fitness class that the time of the fitnessClass he wants to check
     * in. That is considered as time conflict.
     *
     * @param fitnessClass The fitness class that the mmember wants to check in.
     * @param member       The member that are going to check in a class
     * @return True if there is time conflict, false otherwise.
     */
    private boolean isTimeConflict(FitnessClass fitnessClass, Member member) {
        String classTime = fitnessClass.getTime().getDateTime();
        for (int i = 0; i < classSchedule.getNumClasses(); i++) {
            if (classSchedule.getFitnessClasses()[i].isRegistered(member)) {
                if (classTime.equals(classSchedule.getFitnessClasses()[i].getTime().getDateTime())) {
                    return true;
                }
            }
        }
        return false;
    }
}
