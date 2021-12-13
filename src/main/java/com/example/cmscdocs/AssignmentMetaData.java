package com.example.cmscdocs;

import java.io.File;
import java.util.List;

/**
 * This contains all the data inputted by the user. Such data element class
 * was created due to abstraction and easier retrieval of data between
 * classes that interact with it.
 * @author Nathan Tablang
 */
public class AssignmentMetaData {

    private String assignmentName;
    private String programDescription;
    private String classCode;
    private String instructor;
    private String assignmentNum;
    private List<File> GitHubSS;
    private List<File> UMLSS;

    /**
     * A parameterized constructor that sets up the inputted elements to their corresponding fields.
     * @param assignmentName - a String referring to the assignment name.
     * @param programDescription - a String referring to the program description of the project.
     * @param classCode - a String referring to the class this project is assigned to.
     * @param instructor - a String referring to the instructor teaching said class.
     * @param assignmentNum - a String referring to the assignment number of the project.
     * @param GitHubSS - a List of files that are associated with GitHub screenshots.
     * @param UMLSS - a List of files that are associated with UML diagrams.
     */
    public AssignmentMetaData(String assignmentName, String programDescription, String classCode, String instructor,
                              String assignmentNum, List<File> GitHubSS, List<File> UMLSS) {
        this.assignmentName = assignmentName;
        this.programDescription = programDescription;
        this.classCode = classCode;
        this.instructor = instructor;
        this.assignmentNum = assignmentNum;
        this.GitHubSS = GitHubSS;
        this.UMLSS = UMLSS;
    }

    /**
     * @return assignmentName - a String referring to the assignment name.
     */
    public String getAssignmentName() {
        return assignmentName;
    }
    /**
     * @return programDescription - a String referring to the program description of the project.
     */
    public String getProgramDescription() {
        return programDescription;
    }
    /**
     * @return classCode - a String referring to the class this project is assigned to.
     */
    public String getClassCode() {
        return classCode;
    }
    /**
     * @return instructor - a String referring to the instructor teaching said class.
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * @return assignmentNum - a String referring to the assignment number of the project.
     */
    public String getAssignmentNum() {
        return assignmentNum;
    }
    /**
     * @return GitHubSS - a List of files that are associated with GitHub screenshots.
     */
    public List<File> getGitHubSS() {
        return GitHubSS;
    }
    /**
     * @return UMLSS - a List of files that are associated with UML diagrams.
     */
    public List<File> getUMLSS() {
        return UMLSS;
    }
}
