package com.example.project3_fitnesschainfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main method will create a scene, build the Scene Graph and initialise the Controller of a JavaFX View.
 *
 * @author Kangwei Zhu, Michael Israel
 */
public class GymManagerMain extends Application {
    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(GymManagerMain.class.getResource("GymManagerView.fxml"));
        Scene scene;
        Parent root;
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setTitle("Project 3 - Gym Manager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            System.out.println("fxml file does not exist");
            throw new RuntimeException(e);
        }
    }

    /**
     * This method loads and initializes the specified Application class on the JavaFX Application Thread.
     *
     * @param args commandLine inputs
     */
    public static void main(String[] args) {
        launch();
    }
}