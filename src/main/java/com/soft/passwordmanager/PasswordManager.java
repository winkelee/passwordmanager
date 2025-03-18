package com.soft.passwordmanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import javax.crypto.SecretKey;
import java.nio.file.Files;
import java.nio.file.Path;

public class PasswordManager extends Application {

    public static Stage primaryStage;
    public static SecretKey key;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Path masterPasswordPath = PasswordFileController.getAppDataPath("master.enc");
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
        System.out.println("BOOTING UP COMPLETED");
    }

    public static void displayPopUp(String labelText, String buttonText){
        Popup popup = new Popup();
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        Label label = new Label(labelText);
        label.getStyleClass().add("text-label-primary");
        Button closeButton = new Button(buttonText);
        closeButton.getStyleClass().add("button-primary");
        closeButton.setOnAction(eventButton -> popup.hide());
        layout.getChildren().addAll(label, closeButton);
        popup.getContent().add(layout);
        layout.setStyle("-fx-background-color: #282828; -fx-border-color: #FBF1C7; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        popup.show(PasswordManager.primaryStage);
    }

    public static void main(String[] args){
        launch();
    }

}


