package com.soft.passwordmanager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.nio.file.Path;

public class LogInController {

    @FXML
    private Label loginLabelOne;
    @FXML
    private AnchorPane baseLayout;
    @FXML
    private TextField masterPasswordField;
    @FXML
    private Button checkButton;

    @FXML
    private void initialize(){
        loginLabelOne.getStyleClass().add("text-label-primary");
        baseLayout.getStyleClass().add("base-layout");
        masterPasswordField.getStyleClass().add("text-field-primary");
        checkButton.getStyleClass().add("button-primary");

        masterPasswordField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    checkMasterPassword();
                }
                }
        });

        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkMasterPassword();
            }
        });

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

    public void checkMasterPassword(){
        String enteredPasswordPlainText = masterPasswordField.getText();
        Path pathToMasterPassword = PasswordFileController.getAppDataPath("master");
        try {
            boolean isValidMasterPassword = PasswordFileController.verifyMasterPassword(enteredPasswordPlainText, pathToMasterPassword);
            if (isValidMasterPassword){
                PasswordManager.key = PasswordCryptography.deriveKey(enteredPasswordPlainText, PasswordFileController.getSalt(pathToMasterPassword));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main_view.fxml"));
                Parent view = loader.load();
                Scene scene = new Scene(view, 300, 600);
                scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                PasswordManager.primaryStage.setScene(scene);
                PasswordManager.primaryStage.hide();
                PasswordManager.primaryStage.show();
            } else{
                displayPopUp("Oops! This does not look like a valid password.", "Close");
            }
        } catch (Exception e) {
            e.printStackTrace();
            displayPopUp("Something went wrong.", "Close");
        }
    }
}

