package com.soft.passwordmanager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class EditController {
    @FXML
    private TextField websiteDomain;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private HBox layout;
    @FXML
    private AnchorPane baseLayout;

    public void setCredentialData(Credentials credentials){
        websiteDomain.setText(credentials.getHostUrl());
        usernameField.setText(credentials.getUsername());
        passwordField.setText(credentials.getPassword());
        setStyles();

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String newDomain = websiteDomain.getText();
                String newUsername = usernameField.getText();
                String newPassword = passwordField.getText();
                byte[] iv = PasswordCryptography.generateIV();
                try {
                    String encryptedPassword = PasswordCryptography.encrypt(newPassword, PasswordManager.key, iv);
                    Credentials credentials = new Credentials(newUsername, encryptedPassword, newDomain);
                    PasswordFileController.saveCredential(newDomain, credentials, encryptedPassword, iv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void setStyles(){
        Insets rootInsets = new Insets(20, 20, 20, 20);
        layout.setPadding(rootInsets);
        baseLayout.getStyleClass().add("base-layout"); // Serious issue with all of this
        layout.getStyleClass().add("details-layout");           //Buttons must have their own class
        websiteDomain.getStyleClass().add("text-field-primary");         //Then one for headers, primary text and secondary text
        usernameField.getStyleClass().add("text-field-primary"); //And another one for TextFields so that we maintain consistency
        passwordField.getStyleClass().add("text-field-primary");
        saveButton.getStyleClass().add("button-primary");
        cancelButton.getStyleClass().add("button-primary");
    }
}
