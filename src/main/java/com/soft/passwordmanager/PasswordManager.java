package com.soft.passwordmanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Path;

public class PasswordManager extends Application {

    public static Stage primaryStage;
    public static final ObservableList<Credentials> credentialsTest = FXCollections.observableArrayList(
            new Credentials("username1", "password1", "example1.com"),
            new Credentials("username2", "password2", "example2.com"),
            new Credentials("username3", "password3", "example3.com")
    );
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Path masterPasswordPath = PasswordFileController.getAppDataPath("master");
        FXMLLoader loader;
        if (Files.exists(masterPasswordPath)){
            loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("signup-view.fxml"));
        }
        Parent view = loader.load(); //java.lang.IllegalStateException: Location is not set.
        Scene scene = new Scene(view, 300, 600);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(1200);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch();
    }

}
