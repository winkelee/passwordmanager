package com.soft.passwordmanager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;

import java.nio.file.Path;


public class SignUpController {
    @FXML
    private AnchorPane baseLayout;
    @FXML
    private Label signupLabelOne;
    @FXML
    private Label signupLabelTwo;
    @FXML
    private TextField masterPasswordField;
    @FXML
    private Button addButton;

    @FXML
    public void initialize(){
        baseLayout.getStyleClass().add("base-layout");
        signupLabelOne.getStyleClass().add("text-label-primary");
        signupLabelTwo.getStyleClass().add("text-label-primary");
        masterPasswordField.getStyleClass().add("text-field-primary");
        addButton.getStyleClass().add("button-primary");

        masterPasswordField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    String plaintextMasterPassword = masterPasswordField.getText();
                    Path masterPasswordPath = PasswordFileController.getAppDataPath("master");
                    try {
                        PasswordFileController.saveMasterPassword(plaintextMasterPassword, masterPasswordPath);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_view.fxml"));
                        Parent view = loader.load(); //java.lang.IllegalStateException: Location is not set.
                        Scene scene = new Scene(view, 300, 600);
                        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                        PasswordManager.primaryStage.setScene(scene);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Popup popup = new Popup();
                        Label label = new Label("Something went wrong.");
                        label.setStyle(" -fx-background-color: white;");
                        popup.getContent().add(label);
                        label.setMinHeight(50);
                        label.setMinWidth(80);
                        popup.show(PasswordManager.primaryStage);
                    }
                }
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String plaintextMasterPassword = masterPasswordField.getText();
                Path masterPasswordPath = PasswordFileController.getAppDataPath("master");
                try {
                    PasswordFileController.saveMasterPassword(plaintextMasterPassword, masterPasswordPath);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("main_view.fxml"));
                    Parent view = loader.load(); //java.lang.IllegalStateException: Location is not set.
                    Scene scene = new Scene(view, 300, 600);
                    scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                    PasswordManager.primaryStage.setScene(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                    Popup popup = new Popup();
                    Label label = new Label("Something went wrong.");
                    label.setStyle(" -fx-background-color: white;");
                    popup.getContent().add(label);
                    label.setMinHeight(50);
                    label.setMinWidth(80);
                    popup.show(PasswordManager.primaryStage);
                }
            }
        });
    }

}
