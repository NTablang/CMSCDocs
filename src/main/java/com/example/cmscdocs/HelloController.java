package com.example.cmscdocs;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import javax.swing.JOptionPane;

public class HelloController implements Initializable {
    // Attributes
    DataManager dataMgr;

    // SideBar
    @FXML
    private ToggleGroup Folders, MOSS;
    @FXML
    private CheckBox srcMark, docMark, writeUp, gitHubSS, umlSS;
    @FXML
    private ImageView darkToggle, lightToggle;
    @FXML
    private VBox sideBar;
    @FXML
    private RadioButton folder1, folder2, moss1, moss2;
    @FXML
    private Label checklistLabel, title, mossLabel;

    //Setup Page
    private File inputFile, destinationFile, srcFolder, docFolder;
    @FXML
    private TextField assignmentName;
    @FXML
    private TextArea classCode, instructor, assignNum, programDescription;
    @FXML
    private TextField srcField, docField, javaFolder, destinationFolder;
    @FXML
    private Button inputSRC, inputDOC, inputROOT, inputDestination, generate, clear;

    // Boxes
    @FXML
    private HBox AssignmentBox, ClassInfoHBox;
    @FXML
    private Label assignmentNameLabel, programDescriptionLabel;
    @FXML
    private VBox bodyPane;

    // WriteUps Page
    @FXML
    private TextArea lesson1, lesson2, lesson3;

    // Documentation Page
    @FXML
    private Button inputGitSS, inputUMLSS;
    @FXML
    private ListView<String> listScreenshots, UMLScreenshots;
    private List<File> gitSS, umlScreenS;

    //Tabs
    @FXML
    private Tab SetUpHeader, WriteUpHeader, GitHubUMLHeader;
    @FXML
    private HBox bottomBar;
    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Setups Page
        inputROOT.setOnAction(new ButtonHandler());
        inputDestination.setOnAction(new ButtonHandler());
        inputSRC.setOnAction(new ButtonHandler());
        inputDOC.setOnAction(new ButtonHandler());

        // Lesson Learned Page
        lesson1.setOnMouseExited(new MouseListener());
        lesson2.setOnMouseExited(new MouseListener());
        lesson3.setOnMouseExited(new MouseListener());

        // Documentation page
        inputGitSS.setOnAction(new ButtonHandler());
        inputUMLSS.setOnAction(new ButtonHandler());

        // Finalize Buttons
        generate.setOnAction(new ButtonHandler());
        clear.setOnAction(new ButtonHandler());

    }

    // Auxilliary Classes
    private class MouseListener implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            // If a mouse cursor enters or leaves the text-field associated with the lesson learned prompts
            // this mechanism periodically checks if there is a text or not. A necessary condition to
            // be able to generate the files necessary for submission.
            if (event.getTarget() == lesson1 || event.getTarget() == lesson2 || event.getTarget() == lesson3) {
                try {
                    int lesson1Len = lesson1.getText().trim().length();
                    int lesson2Len = lesson2.getText().trim().length();
                    int lesson3Len = lesson3.getText().trim().length();
                    if (lesson1 != null && lesson2 != null && lesson3 != null) {
                        writeUp.setSelected(true);
                    }
                    else {
                        writeUp.setSelected(false);
                    }
                    if (lesson1Len != 0 && lesson2Len != 0 && lesson3Len != 0) {
                        writeUp.setSelected(true);
                    }
                    else {
                        writeUp.setSelected(false);
                    }
                }
                catch (NullPointerException e) {
                    JOptionPane.showInputDialog(null, e.getMessage());
                }
            }
        }
    }
    private class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            // If a user selects to choose the folder containing the .src files manually
            if (event.getTarget() == inputSRC) {
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Specifically choose the SRC directory");
                File selectedDirectory = chooser.showDialog(null);
                srcFolder = selectedDirectory;

                if (selectedDirectory != null) {
                    // if input file cannot be read, show error thru dialog box
                    if (!selectedDirectory.canRead()) {
                        try {
                            throw new IOException();
                        } catch (IOException e1) {
                            JOptionPane.showInputDialog(null, e1.getMessage());
                        }
                    }
                    System.out.println(selectedDirectory.getAbsolutePath());
                    String path = selectedDirectory.getAbsolutePath();
                    srcField.setText(path);
                    srcMark.setSelected(true);

                    // The GUI needs the inputSRC to be non-null in order to indicate the user that all files are ready
                    // and the finished documentation is ready to be generated.
                    if (isAllSatisfied()) {
                        generate.setStyle("-fx-background-color: green");
                    }
                }
            }
            // If a user selects to choose the folder containing the doc files manually
            else if (event.getTarget() == inputDOC) {
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Specifically choose the DOC directory");
                File selectedDirectory = chooser.showDialog(null);
                docFolder = selectedDirectory;

                if (selectedDirectory != null) {
                    // if input file cannot be read, show error thru dialog box
                    if (!selectedDirectory.canRead()) {
                        try {
                            throw new IOException();
                        } catch (IOException e1) {
                            JOptionPane.showInputDialog(null, e1.getMessage());
                        }
                    }
                    System.out.println(selectedDirectory.getAbsolutePath());
                    String path = selectedDirectory.getAbsolutePath();
                    docField.setText(path);
                    docMark.setSelected(true);
                }
                // The GUI needs the inputDOC to be non-null in order to indicate the user that all files are ready
                // and the finished documentation is ready to be generated.
                if (isAllSatisfied()) {
                    generate.setStyle("-fx-background-color: green");
                }
            }
            // If a user selects to choose the folder containing the ROOT files manually
            else if (event.getTarget() == inputROOT) {
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Specifically choose the ROOT directory");
                File selectedDirectory = chooser.showDialog(null);
                inputFile = selectedDirectory;
                if (selectedDirectory != null) {
                    // if input file cannot be read, show error thru dialog box
                    if (!selectedDirectory.canRead()) {
                        try {
                            throw new IOException();
                        } catch (IOException e1) {
                            // TODO: Uncomment this
                            //JOptionPane.showInputDialog(null, e1.getMessage());
                        }
                    }
                    System.out.println(selectedDirectory.getAbsolutePath());
                    String path = selectedDirectory.getAbsolutePath();
                    javaFolder.setText(path);

                    // Checks if the ROOT directory as inputted by the user also contains the src and doc directories
                    // if it does, the user don't have to automatically do it manually.
                    File[] existingFiles = inputFile.listFiles();
                    for (File file: existingFiles) {
                        if (file.toString().contains("/src")) {
                            srcFolder = file;
                            srcField.setText(srcFolder.toString());
                            srcMark.setSelected(true);
                        }
                        if (file.toString().contains("/doc")) {
                            docFolder = file;
                            docField.setText(docFolder.toString());
                            docMark.setSelected(true);
                        }
                    }
                }

                // The GUI needs this to be non-null in order to indicate the user that all files are ready
                // and the finished documentation is ready to be generated.
                if (isAllSatisfied()) {
                    generate.setStyle("-fx-background-color: green");
                }
            }
            // The destination that the finished files will be contained in.
            else if (event.getTarget() == inputDestination) {
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("Specifically choose the DESTINATION directory");
                File selectedDirectory = chooser.showDialog(null);
                destinationFile = selectedDirectory;
                if (selectedDirectory != null) {
                    // if input file cannot be read, show error thru dialog box
                    if (!selectedDirectory.canRead()) {
                        try {
                            throw new IOException();
                        } catch (IOException e1) {
                            JOptionPane.showInputDialog(null, e1.getMessage());
                        }
                    }
                    System.out.println(selectedDirectory.getAbsolutePath());
                    String path = selectedDirectory.getAbsolutePath();
                    destinationFolder.setText(path);
                }
                // The GUI needs this to be non-null in order to indicate the user that all files are ready
                // and the finished documentation is ready to be generated.
                if (isAllSatisfied()) {
                    generate.setStyle("-fx-background-color: green");
                }
            }
            // Necessary for GitHub Screenshots
            else if (event.getTarget() == inputGitSS) {
                FileChooser chooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.jpg)", "*.jpg", "*.png", "*.png");
                chooser.getExtensionFilters().add(extFilter);

                chooser.setTitle("Select GitHub Screenshots");

                gitSS = chooser.showOpenMultipleDialog(null);
                if (gitSS == null) return;
                for (File file: gitSS) {
                    if (listScreenshots.getItems().contains(file.toString()))
                        continue;
                    listScreenshots.getItems().add(file.toString());
                }
                gitHubSS.setSelected(true);
                // The GUI needs this to be non-null in order to indicate the user that all files are ready
                // and the finished documentation is ready to be generated.
                if (isAllSatisfied()) {
                    generate.setStyle("-fx-background-color: green");
                }
            }
            // Necessary for UML Screenshots
            else if (event.getTarget() == inputUMLSS) {
                FileChooser chooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.jpg)", "*.jpg", "*.png", "*.png");
                chooser.getExtensionFilters().add(extFilter);
                chooser.setTitle("Select UML Screenshots");
                umlScreenS = chooser.showOpenMultipleDialog(null);
                if (umlScreenS == null) return;
                for (File file: umlScreenS) {
                    if (UMLScreenshots.getItems().contains(file.toString()))
                        continue;
                    UMLScreenshots.getItems().add(file.toString());
                }
                umlSS.setSelected(true);
                // The GUI needs this to be non-null in order to indicate the user that all files are ready
                // and the finished documentation is ready to be generated.
                if (isAllSatisfied()) {
                    generate.setStyle("-fx-background-color: green");
                }
            }
            // Checks if all conditions above are satisfied and if they are, it creates an instance of AssignmentMetaData
            // that will be using by ulility classes as it bundles up all the information inputted by the user into one object.
            else if (event.getTarget() == generate) {
                RadioButton selectedFolders = (RadioButton)Folders.getSelectedToggle();
                RadioButton constructMOSS = (RadioButton)MOSS.getSelectedToggle();

                if (isAllSatisfied()) {
                    AssignmentMetaData metaData = new AssignmentMetaData(assignmentName.getText(), programDescriptionLabel.getText(), classCode.getText(), instructor.getText(),
                            assignNum.getText(), gitSS, umlScreenS);
                    dataMgr = new DataManager(destinationFile.getAbsolutePath(), metaData);
                    generate.setStyle("-fx-background-color: green");
                    try {
                        // Checks if the user wants to put files in a new folder
                        if (selectedFolders.getText().equals("New Folder")) {
                            dataMgr.makeCompleteDirectory();
                            dataMgr.createLessonWord(lesson1.getText(), lesson2.getText(), lesson3.getText());
                            dataMgr.copySRC(new File(srcFolder.getAbsolutePath()));
                            dataMgr.copyDOC(new File(docFolder.getAbsolutePath()));
                            dataMgr.createGithubDocumentation();
                            dataMgr.createUMLDocumentation();
                        }
                        // Checks if the user wants to put files into an existing one.
                        if (selectedFolders.getText().equals("Add to existing folder")) {
                            File[] files = destinationFile.listFiles();
                            File completeFolder = new File(destinationFile.getAbsolutePath());
                            File mossFolder = null;
                            for (File file: files) {
                                if (file.toString().contains("Complete")) {
                                    completeFolder = new File(file.toString());
                                }
                                if (file.toString().contains("MOSS")) {
                                    mossFolder = new File(file.toString());
                                }
                            }

                            // If the destination folder does not contain said file/directory, allow copy
                            String[] subfilesComplete = completeFolder.list();
                            for (String file: subfilesComplete) {
                                if (!file.contains("src")) {
                                    dataMgr.copySRC(srcFolder);
                                }
                                if (!file.contains("doc")) {
                                    dataMgr.copyDOC(docFolder);
                                }
                                if (!file.contains("LessonLearned")) {
                                    dataMgr.createLessonWord(lesson1.getText(), lesson2.getText(), lesson3.getText());
                                }
                                if (!file.contains("Github")) {
                                    dataMgr.createGithubDocumentation();
                                }
                                if (!file.contains("UML")) {
                                    dataMgr.createUMLDocumentation();
                                }
                            }
                            // If the user wants to also construct MOSS folders.
                            if (mossFolder == null && constructMOSS.getText().equals("Yes")) {
                                dataMgr.makeMOSSDirectory(srcFolder);
                            }
                        }
                        // If the user wants to also construct MOSS folders.
                        if (constructMOSS.getText().equals("Yes")) {
                            dataMgr.makeMOSSDirectory(srcFolder);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // Gives out the confirmation dialog.
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Generated succesfully at your destination folder.");
                    alert.setContentText("Please review .docx files and add any necessary files before compressing.");
                    alert.showAndWait();
                }
                // If the user wants to generate finished files but not all criteria are met (listed above), it will
                // not allow the user to complete the process.
                else {
                    warnIncompleted();
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Review all incomplete fields.");
                    alert.showAndWait();
                }
            }
            // Clears out all inputs.
            else if (event.getTarget() == clear) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Clear Confirmation");
                alert.setHeaderText("Are you sure you want to clear all?");

                ButtonType button1 = new ButtonType("Yes");
                ButtonType button2 = new ButtonType("No");

                alert.getButtonTypes().setAll(button1, button2);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == button1) {
                    System.out.println("chose yes");
                    inputFile = null;
                    destinationFile = null;
                    srcFolder = null;
                    docFolder = null;
                    srcMark.setSelected(false);
                    srcMark.setStyle("-fx-text-fill: black;");

                    docMark.setSelected(false);
                    docMark.setStyle("-fx-text-fill: black;");

                    writeUp.setSelected(false);
                    writeUp.setStyle("-fx-text-fill: black;");

                    gitHubSS.setSelected(false);
                    gitHubSS.setStyle("-fx-text-fill: black;");

                    umlSS.setSelected(false);
                    umlSS.setStyle("-fx-text-fill: black;");



                    srcField.setText(null);
                    srcField.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    docField.setText(null);
                    docField.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    javaFolder.setText(null);
                    javaFolder.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    destinationFolder.setText(null);
                    destinationFolder.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    assignmentName.setText(null);
                    assignmentName.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    classCode.setText(null);
                    classCode.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    instructor.setText(null);
                    instructor.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    assignNum.setText(null);
                    assignNum.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    programDescription.setText(null);
                    programDescription.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    lesson1.setText(null);
                    lesson1.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    lesson2.setText(null);
                    lesson2.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    lesson3.setText(null);
                    lesson3.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    listScreenshots.getItems().removeAll(listScreenshots.getItems());
                    listScreenshots.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                    UMLScreenshots.getItems().removeAll(UMLScreenshots.getItems());
                    UMLScreenshots.setStyle("-fx-border-color: black; fx-border-radius: 20px");

                }

            }

        }
        // Checks all necessary meta data to be filled up as well as other conditions
        private boolean isAllSatisfied() {
            if (destinationFile == null)
                return false;
            if (assignmentName == null)
                return false;
            if (programDescription == null)
                return false;
            if (classCode == null)
                return false;
            if (instructor == null)
                return false;
            if (assignNum == null)
                return false;
            if (!srcMark.isSelected())
                return false;
            if (!docMark.isSelected())
                return false;
            if (!writeUp.isSelected())
                return false;
            if (!gitHubSS.isSelected())
                return false;
            if (!umlSS.isSelected())
                return false;
            if (inputFile == null || !inputFile.canRead())
                return false;
            if (destinationFile == null || !destinationFile.canRead())
                return false;
            return true;
        }
        // Necessary for the GUI to tell the user which fields are incomplete.
        private void warnIncompleted() {
            //.setStyle("-fx-text-fill: maroon; -fx-font-weight: bold;");
            int assignmentNameLen = assignmentName.getText().trim().length();
            if (assignmentNameLen == 0) {
                //System.out.println("here1");
                //assignmentNameLabel.setStyle("-fx-text-fill: red;");
                assignmentName.setStyle("-fx-border-color: red; fx-border-radius: 20px");
            }
            int programDescriptionLen = programDescription.getText().trim().length();
            if (programDescriptionLen == 0) {
                //programDescriptionLabel.setStyle("-fx-text-fill: red;");
                programDescription.setStyle("-fx-background-color: #f5ccce; -fx-border-color: red; fx-border-radius: 20px");
            }
            int classCodeLen = classCode.getText().trim().length();
            int instructorLen = instructor.getText().trim().length();
            int assignNumLen = assignNum.getText().trim().length();

            if (classCodeLen == 0 || instructorLen == 0 || assignNumLen == 0) {
                ClassInfoHBox.setStyle("-fx-text-fill: red;");
                if (classCodeLen == 0) {
                    classCode.setStyle("-fx-background-color: #f5ccce; -fx-border-color: red; fx-border-radius: 20px");
                }
                if (instructorLen == 0) {
                    instructor.setStyle("-fx-background-color: #f5ccce; -fx-border-color: red; fx-border-radius: 20px");
                }
                if (assignNumLen == 0) {
                    assignNum.setStyle("-fx-background-color: #f5ccce; -fx-border-color: red; fx-border-radius: 20px");
                }
            }

            if (inputFile == null || !inputFile.canRead()) {
                javaFolder.setStyle("-fx-border-color: red; fx-border-radius: 20px");
            }
            if (destinationFile == null || !destinationFile.canRead()) {
                destinationFolder.setStyle("-fx-border-color: red; fx-border-radius: 20px");
            }

            if (!srcMark.isSelected()) {
                srcField.setStyle("-fx-border-color: red; fx-border-radius: 20px");
                srcMark.setStyle("-fx-text-fill: red;");

            }

            if (!docMark.isSelected()) {
                docField.setStyle("-fx-border-color: red; fx-border-radius: 20px");
                docMark.setStyle("-fx-text-fill: red;");

            }

            if (!writeUp.isSelected()) {
                int lesson1Len = lesson1.getText().trim().length();
                int lesson2Len = lesson2.getText().trim().length();
                int lesson3Len = lesson3.getText().trim().length();
                if (lesson1Len == 0) {
                    lesson1.setStyle("-fx-border-color: red; fx-border-radius: 20px");
                }
                if (lesson2Len == 0) {
                    lesson2.setStyle("-fx-border-color: red; fx-border-radius: 20px");
                }
                if (lesson3Len == 0) {
                    lesson3.setStyle("-fx-border-color: red; fx-border-radius: 20px");
                }
                writeUp.setStyle("-fx-text-fill: red;");

            }

            if (!gitHubSS.isSelected()) {
                listScreenshots.setStyle("-fx-border-color: red; fx-border-radius: 20px");
                gitHubSS.setStyle("-fx-text-fill: red;");
            }

            if (!umlSS.isSelected()) {
                UMLScreenshots.setStyle("-fx-border-color: red; fx-border-radius: 20px");
                umlSS.setStyle("-fx-text-fill: red;");

            }
        }
    }

}
