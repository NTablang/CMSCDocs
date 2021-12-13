package com.example.cmscdocs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class launches the FXML responsible for GUI and Controller interactions.
 * This essentially kickstarts the project.
 *
 * @author Nathan Tablang
 */
public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root, 1400, 800);
        scene.getStylesheets().add(getClass().getResource("otherStyle.css").toExternalForm());
        stage.setScene(scene);

        stage.setTitle("CMSC Documentation Builder");

        stage.show();
    }
}