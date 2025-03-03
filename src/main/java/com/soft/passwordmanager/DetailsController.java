package com.soft.passwordmanager;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class DetailsController {

    @FXML
    private Label websiteLabel;
    @FXML
    private Label usernameField;
    @FXML
    private Button copyButtonUsername;
    @FXML
    private Button copyButtonPassword;
    @FXML
    private Label passwordField;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private HBox layout;
    @FXML
    private AnchorPane baseLayout;

    public void setCredentialData(Credentials credentials){
        websiteLabel.setText(credentials.getHostUrl());
        usernameField.setText(credentials.getUsername());
        passwordField.setText(credentials.getPassword());
        setStyles();
    }

    public void setStyles(){
        Insets rootInsets = new Insets(20, 20, 20, 20);
        layout.setPadding(rootInsets);
        baseLayout.getStyleClass().add("base-layout-details");
        layout.getStyleClass().add("details-layout");
        websiteLabel.getStyleClass().add("website-label");
        usernameField.getStyleClass().add("username-field-details");
        copyButtonUsername.getStyleClass().add("copy-button-username-details");
        passwordField.getStyleClass().add("password-field-details");
        editButton.getStyleClass().add("edit-button");
        deleteButton.getStyleClass().add("delete-button");
        copyButtonPassword.getStyleClass().add("copy-button-password-details");
    }
}
