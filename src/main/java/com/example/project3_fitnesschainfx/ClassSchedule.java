package com.example.project3_fitnesschainfx;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ClassSchedule is the class that serve the function of storing all the fitnesses classes that this fitness chain has.
 * In this project all the FitnessClasses is stored in an array
 *
 * @author Michael Israel, Kangwei Zhu
 */
public class ClassSchedule {
    private FitnessClass[] fitnessClasses;
    private int numClasses;
    private ArrayList<String> warning = new ArrayList<>();
    /**
     * Constructor method without parameter.
     * Initialize the fitnessClasses array to null and the number of classes to zero.
     */
    public ClassSchedule() {
        numClasses = 0;
        fitnessClasses = null;
    }

    /**
     * Constructor method with parameter. Giving values to the instance variable fitnessclasses and numClasses.
     *
     * @param fitnessClasses The array that store all the FitnessClass objects.
     * @param numClasses     The number of classes this fitness chain has.
     */
    public ClassSchedule(FitnessClass[] fitnessClasses, int numClasses) {
        this.fitnessClasses = fitnessClasses;
        this.numClasses = numClasses;
    }

    /**
     * This method return the warning arraylist, which is the array of Strings that going to be shown on the textArea
     * @return the warning ArrayList that holds Strings.
     */
    public ArrayList<String> getWarning() {
        return this.warning;
    }

    /**
     * This method handle the command "LS", which is the command that load the fitness class schedule from a text file
     * to the class schedule in the software system.
     * @param classSchedule  The file associated with information on the gym classes
     */
    public void LS(File classSchedule) {
        warning.clear();
        String[] lines = readFiles(classSchedule);
        FitnessClass[] fitnessClasses = new FitnessClass[Integer.parseInt(lines[0])];
        int index = 0;
        for (int i = 1; i < lines.length; i++) {
            String cmdLine = lines[i];
            String[] infos = cmdLine.split("\\s");
            String location = infos[GymManagerController.INDEX_OF_LOCATION].toUpperCase();
            Time classTime = Time.valueOf(infos[GymManagerController.INDEX_OF_DAYTIME].toUpperCase());
            Location classLocation = Location.valueOf(infos[GymManagerController.INDEX_OF_LOCATION].toUpperCase());
            if (isValidLocation(location)) {
                FitnessClass fitnessClass = new FitnessClass(infos[GymManagerController.INDEX_OF_CLASS_NAME], infos[GymManagerController.INDEX_OF_INSTRUCTOR],
                        classTime, classLocation);
                fitnessClasses[index++] = fitnessClass;
            }
        }
        this.fitnessClasses = fitnessClasses;
        this.numClasses = fitnessClasses.length;
        printClassSchedule();
    }

    /**
     * This method is used when getting the number of classes.
     *
     * @return The number of classes that this fitness chain has.
     */
    public int getNumClasses() {
        return this.numClasses;
    }

    /**
     * This method is used when getting the array that hold all the fitnessClasses.
     *
     * @return The array that hold all fitnessClasses.
     */
    public FitnessClass[] getFitnessClasses() {
        return this.fitnessClasses;
    }

    /**
     * This method is used when displaying all the fitness class schedule.
     */
    public void printClassSchedule() {
        warning.clear();
        warning.add("-Fitness classes loaded-");
        for (int i = 0; i < numClasses; i++) {
            warning.add(fitnessClasses[i].toString());
        }
        warning.add("-end of class list.");
    }

    /**
     * This method is used when we are checking whether a FitnessClass exists or not.
     *
     * @param checkClass the FitnessClass we want to check.
     * @return true if it exists, otherwise false.
     */
    public boolean isFitnessClassExist(FitnessClass checkClass) {
        warning.clear();
        String className = checkClass.getFitnessClassName();
        String instructor = checkClass.getInstructor();
        Location location = checkClass.getLocation();
        boolean nameFlag = false;
        boolean instructorFlag = false;
        for (int i = 0; i < numClasses; i++) {
            if (fitnessClasses[i].equals(checkClass)) {
                return true;
            }
            if (fitnessClasses[i].getFitnessClassName().equalsIgnoreCase(className)) {
                nameFlag = true;
            }
            if (fitnessClasses[i].getInstructor().equalsIgnoreCase(instructor)) {
                instructorFlag = true;
            }
        }
        if (!nameFlag) {
            warning.add(className + " - class does not exist.");
            return false;
        } else if (!instructorFlag) {
            warning.add(instructor + " - instructor does not exist.");
            return false;
        } else {
            warning.add(className + " by " + instructor + " does not exist at " + location);
            return false;
        }
    }

    /**
     * Get a specific class from the array that hold all the FitnessClasses object
     *
     * @param fitnessClass The class we want to retrieve
     * @return null if not found, specific fitnessClass object if found.
     */
    public FitnessClass getFitnessClass(FitnessClass fitnessClass) {
        for (int i = 0; i < numClasses; i++) {
            if (fitnessClass.equals(fitnessClasses[i])) {
                return fitnessClasses[i];
            }
        }
        return null;
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
        warning.add(loc + ": invalid location!");
        return false;
    }

    /**
     * This method is used when reading lines from text file.
     *
     * @param inputFile The file that are going to be read
     * @return The String array that contains all the lines. Each element in this array is the one line in the text file.
     */
    private String[] readFiles(File inputFile) {
        try {
            Scanner sc = new Scanner(inputFile);
            String line;
            int countNumOfLine = 0;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                if (line == null || line.length() == 0) {
                    break;
                }
                countNumOfLine++;
            }
            String[] lines = new String[countNumOfLine + 1];
            lines[0] = Integer.toString(countNumOfLine);
            sc = new Scanner(inputFile);
            for (int i = 1; i < lines.length; i++) {
                lines[i] = sc.nextLine();
            }
            return lines;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}


