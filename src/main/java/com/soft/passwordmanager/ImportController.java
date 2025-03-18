package com.soft.passwordmanager;

import com.opencsv.exceptions.CsvValidationException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class ImportController {
    @FXML
    private AnchorPane baseLayout;
    @FXML
    private Label newCredentialsLabel;
    @FXML
    private Label importLabel;
    @FXML
    private Button importButton;
    @FXML
    private Label nativeLabel;
    @FXML
    private TextField addUsernameField;
    @FXML
    private TextField addPasswordField;
    @FXML
    private TextField addURLField;
    @FXML
    private Button addButton;
    @FXML
    private Label exportLabel;
    @FXML
    private Button exportButton;
    @FXML
    private VBox layout;
    private MainController mainController;


    public void setMainController(MainController mainController){ //We need to get a reference to the main controller as we need to refresh the listview.
        this.mainController = mainController;
    }

    @FXML
    public void initialize(){
        baseLayout.getStyleClass().add("base-layout");
        newCredentialsLabel.getStyleClass().add("header-label");
        importLabel.getStyleClass().add("small-primary-label");
        importButton.getStyleClass().add("button-primary");
        nativeLabel.getStyleClass().add("small-primary-label");
        addUsernameField.getStyleClass().add("text-field-primary");
        addPasswordField.getStyleClass().add("text-field-primary");
        addURLField.getStyleClass().add("text-field-primary");
        addButton.getStyleClass().add("button-primary");
        exportButton.getStyleClass().add("button-primary");
        exportLabel.getStyleClass().add("text-label-primary");
        Insets baseLayoutInsets = new Insets(20, 20, 20, 20);
        layout.setPadding(baseLayoutInsets);

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String newUsername = addUsernameField.getText();
                String plaintextPassword = addPasswordField.getText();
                String newURL = addURLField.getText();

                byte[] iv = PasswordCryptography.generateIV();
                try {
                    String encodedPassword = PasswordCryptography.encrypt(plaintextPassword, PasswordManager.key, iv);
                    Credentials credentials = new Credentials(newUsername, encodedPassword, newURL, iv);  //A bit of a bad implementation here
                    PasswordFileController.saveCredential(newURL + ".enc", credentials, encodedPassword, iv);        //Ideally, we do not need to pass the iv and encoded password. Need to change this in the future.
                    mainController.setItemsInListView(PasswordFileController.getCredentialFiles());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        exportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    PasswordFileController.exportData();

                } catch (Exception e) {
                    PasswordManager.displayPopUp("Something went wrong!", "Close");
                    throw new RuntimeException(e);
                }
            }
        });


        importButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(PasswordManager.primaryStage);
                if (file != null){
                    try {
                        PasswordFileController.readImportedData(file);
                        mainController.setItemsInListView(PasswordFileController.getCredentialFiles());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}