package com.example.cmscdocs;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.commons.io.FileUtils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is in charge of handling utility-based methods that will
 * actually use the APACHE POI API method.
 * @author Nathan Tablang
 */
public class DataManager {
    String destinationFilePath;
    AssignmentMetaData metaData;
    File completeDir;

    /**
     * Parameterized constructor that takes in the destination path and an
     * AssignmentMetaData instance for easier retrieval of data between
     * the controllers and the utility classes.
     * @param destination - a String, the destination path
     * @param metaData - AssignmentMetaData instance that has all the information inputted by the user.
     */
    public DataManager(String destination, AssignmentMetaData metaData) {
        destinationFilePath = destination;
        this.metaData = metaData;

    }

    /**
     * Creates the "_Complete" directory required in CMSC projects.
     * This directory bundles up the src, doc, LessonLearned, UML, and GitHub documentations.
     * @throws IOException
     */
    public void makeCompleteDirectory() throws IOException {
        String destination = destinationFilePath + "/" + metaData.getAssignmentName() + "_Complete";
        new File(destination).mkdir();
        completeDir = new File(destination);
    }

    /**
     * This creates the "_MOSS" directory required in CMSC projects.
     * This directory houses only the .src java files.
     * @param srcFolder - the folder that contains the .src files of the project.
     * @throws IOException
     */
    public void makeMOSSDirectory(File srcFolder) throws IOException {
        String destination = destinationFilePath + "/" + metaData.getAssignmentName() + "_MOSS";
        new File(destination).mkdir();
        File mossSRC = new File(destination);
        String source = srcFolder.getAbsolutePath();
        File srcDir = new File(source);
        try {
            FileUtils.copyDirectory(srcDir, mossSRC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This creates the stylized blue header typically seen in documentations.
     * @param document - an XWPFDocument associated with that documentation paper.
     * @param headerTitle - the header title to be titled.
     */
    public void makeHeader(XWPFDocument document, String headerTitle) {
        XWPFParagraph paragraph1 = document.createParagraph();
        paragraph1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun line1 = paragraph1.createRun();
        XWPFRunCustom.setCustomHeadingSettings(line1);
        /*
        line1.setBold(true);
        line1.setFontSize(14);
        line1.setColor("001959");
        line1.setFontFamily("Times New Roman");
         */

        line1.setText("CMSC " + metaData.getClassCode() + " Assignment " + metaData.getAssignmentNum() + " Implementation");
        line1.addBreak();


        XWPFRun line2 = paragraph1.createRun();
        XWPFRunCustom.setSubHeadingsSettings(line2);
        line2.setText(headerTitle);
        line2.addBreak();
        line2.addBreak();
        /*
        line2.setItalic(true);
        line2.setFontSize(14);
        line2.setColor("001959");
        line2.setFontFamily("Times New Roman");
        line2.setText("Lesson Learned");
        line2.addBreak();
        line2.addBreak();
         */

        XWPFParagraph paragraph2 = document.createParagraph();
        paragraph2.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun line3 = paragraph2.createRun();
        XWPFRunCustom.setPrefaceSettings(line3);
        /*
        line3.setBold(true);
        line3.setFontSize(12);
        line3.setColor("001959");
        line3.setFontFamily("Times New Roman");
         */
        line3.setText("Class: ");

        XWPFRun line4 = paragraph2.createRun();
        XWPFRunCustom.setNormalPrefaceSettings(line4);
        /*
        line4.setBold(false);
        line4.setFontSize(12);
        line4.setColor("001959");
        line4.setFontFamily("Times New Roman");

         */
        line4.setText("CMSC " + metaData.getClassCode());
        line4.addBreak();

        XWPFRun line5 = paragraph2.createRun();
        XWPFRunCustom.setPrefaceSettings(line5);
        line5.setText("Instructor: ");


        XWPFRun line6 = paragraph2.createRun();
        XWPFRunCustom.setNormalPrefaceSettings(line6);
        line6.setText("Prof. " + metaData.getInstructor());
        line6.addBreak();

        XWPFRun descr = paragraph2.createRun();
        XWPFRunCustom.setPrefaceSettings(descr);
        descr.setText("Program Description: ");

        XWPFRun description = paragraph2.createRun();
        XWPFRunCustom.setNormalPrefaceSettings(description);
        description.setText(metaData.getProgramDescription());
        description.addBreak();
        description.addBreak();
    }

    /**
     * This creates the word document associated with writing out the Lesson Learned
     * responses using Apache POI-OOXML API. It opens the Word Document then closes it.
     * @param lesson1 - a String which details the response to the first prompt.
     * @param lesson2 - a String which details the response to the second prompt.
     * @param lesson3 - a String which details the response to the third prompt.
     * @throws IOException
     */
    public void createLessonWord(String lesson1, String lesson2, String lesson3) throws IOException {
        System.out.println("here: " + completeDir.getAbsolutePath());
        String destination = completeDir.getAbsolutePath();
        String fileName = destination + "/" + metaData.getAssignmentName() + "_LessonLearned.docx";

        System.out.println(fileName);
        FileOutputStream out = new FileOutputStream(new File(fileName));
        XWPFDocument document = new XWPFDocument();

        makeHeader(document, "Lesson Learned");

        // Prompt
        XWPFParagraph descriptions = document.createParagraph();
        XWPFRun line1 = descriptions.createRun();
        XWPFRunCustom.setPrefaceSettings(line1);
        line1.setText("Lesson Learned");
        line1.addBreak();

        // Prompt 1
        XWPFRun prompt1 = descriptions.createRun();
        XWPFRunCustom.setNormalPrefaceSettings(prompt1);
        prompt1.setText("Write about your Learning Experience, highlighting your lessons learned "+
                "and learning experience from working on this project.");
        prompt1.addBreak();
        prompt1.addBreak();
        prompt1.setText("What have you learned?");
        prompt1.addBreak();
        // response 1
        XWPFRun response1 = descriptions.createRun();
        response1.setFontFamily("Times New Roman");
        response1.setText(lesson1);
        response1.addBreak();
        response1.addBreak();

        // Prompt 2
        XWPFRun prompt2 = descriptions.createRun();
        XWPFRunCustom.setNormalPrefaceSettings(prompt2);
        prompt2.setText("What did you struggle with?");
        prompt2.addBreak();
        // response 2
        XWPFRun response2 = descriptions.createRun();
        response2.setFontFamily("Times New Roman");
        response2.setText(lesson2);
        response2.addBreak();
        response2.addBreak();

        // Prompt 3
        XWPFRun prompt3 = descriptions.createRun();
        XWPFRunCustom.setNormalPrefaceSettings(prompt3);

        prompt3.setText("What would you do differently on your next project?"+
                " What parts of this assignment were you successful with, and what parts "+
                "(if any) were you not successful with? ");
        prompt3.addBreak();
        // response 3
        XWPFRun response3 = descriptions.createRun();
        response3.setFontFamily("Times New Roman");
        response3.setText(lesson3);
        response3.addBreak();
        response3.addBreak();

        // To save changes
        document.write(out);

        // Close the file
        out.close();
        document.close();
        System.out.println("Wrote to file: " + destinationFilePath);
    }

    /**
     * This creates the word document associated with bundling collect screenshots
     * of GitHub submissions. Works best if the amount of screenshot inputted by
     * the user is more than one. It also scales such screenshots.
     * @throws IOException
     * @throws InvalidFormatException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public void createGithubDocumentation() throws IOException, InvalidFormatException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        XWPFDocument document = new XWPFDocument();
        String destination = completeDir.getAbsolutePath();
        String fileName = destination + "/" + metaData.getAssignmentName() +"_GithubScreenshots.docx";
        FileOutputStream out = new FileOutputStream(new File(fileName));

        makeHeader(document, "GitHub Screenshots");
        // To scale images
        for (File img: metaData.getGitHubSS()) {
            int pageW = 500;
            int pageM = 100;

            XWPFParagraph paragraphIMG = document.createParagraph();
            XWPFRun run = paragraphIMG.createRun();
            String imgFile = img.toString();
            FileInputStream fis = new FileInputStream(imgFile);
            BufferedImage image = ImageIO.read(fis);

            int width = image.getWidth();
            int height = image.getHeight();
            double scaling = 1.0;
            if (width > pageW - 2*pageM) {
                scaling = ((double)(pageW - 2*pageM)) / width;
            }
            run.addPicture(new FileInputStream(imgFile),
                    XWPFDocument.PICTURE_TYPE_PNG,
                    imgFile,
                    Units.toEMU(width * scaling/0.75),
                    Units.toEMU(height * scaling/0.75));
        }

        document.write(out);
        out.close();
        document.close();
    }

    /**
     * This creates the word document associated with bundling collect screenshots
     * of UML diagrams.
     * @throws IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public void createUMLDocumentation() throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        XWPFDocument document = new XWPFDocument();
        String destination = completeDir.getAbsolutePath();
        String fileName = destination + "/" + metaData.getAssignmentName() + "_UMLScreenshots.docx";
        FileOutputStream out = new FileOutputStream(new File(fileName));

        makeHeader(document, "UML Screenshots");

        // To scale images
        for (File img: metaData.getUMLSS()) {
            int pageW = 500;
            int pageM = 100;

            XWPFParagraph paragraphIMG = document.createParagraph();
            XWPFRun run = paragraphIMG.createRun();
            String imgFile = img.toString();
            FileInputStream fis = new FileInputStream(imgFile);
            BufferedImage image = ImageIO.read(fis);
            int width = image.getWidth();
            int height = image.getHeight();
            double scaling = 1.0;
            if (width > pageW - 2*pageM) {
                scaling = ((double)(pageW - 2*pageM)) / width;
            }
            try {
                run.addPicture(new FileInputStream(imgFile),
                        XWPFDocument.PICTURE_TYPE_PNG,
                        imgFile,
                        Units.toEMU(width * scaling/0.75),
                        Units.toEMU(height * scaling/0.75));
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        }

        document.write(out);
        out.close();
        document.close();
    }

    /**
     * Copies the source directory containing the .src files to the
     * assigned locations for the new directory for submission.
     * @param srcDir - the source directory containing the .src files
     * @throws IOException
     */
    public void copySRC(File srcDir) throws IOException {


        new File(completeDir.getAbsolutePath()+"/src").mkdir();
        File newSRC = new File(completeDir.getAbsolutePath()+"/src");
        try {
            FileUtils.copyDirectory(srcDir, newSRC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Copies the source directory containing the .html+ files to the
     * assigned locations for the new directory for submission.
     * @param docSource - the source directory containing the doc files
     * @throws IOException
     */
    public void copyDOC(File docSource) throws IOException {


        new File(completeDir.getAbsolutePath()+"/doc").mkdir();
        File newDOC = new File(completeDir.getAbsolutePath()+"/doc");
        try {
            FileUtils.copyDirectory(docSource, newDOC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



