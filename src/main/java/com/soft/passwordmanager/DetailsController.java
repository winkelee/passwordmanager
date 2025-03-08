package com.soft.passwordmanager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

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

    public void setCredentialData(Credentials credentials, MainController mainController){
        websiteLabel.setText(credentials.getHostUrl());
        usernameField.setText(credentials.getUsername());
        passwordField.setText(credentials.getPassword());
        setStyles();

        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EditController editController = (EditController) mainController.loadView("edit-view.fxml");
                if (editController != null){
                    editController.setCredentialData(credentials);
                }
            }
        });
    }

    public void setStyles(){
        Insets rootInsets = new Insets(20, 20, 20, 20);
        layout.setPadding(rootInsets);
        baseLayout.getStyleClass().add("base-layout"); // Serious issue with all of this
        layout.getStyleClass().add("details-layout");           //Buttons must have their own class
        websiteLabel.getStyleClass().add("header-label");         //Then one for headers, primary text and secondary text
        usernameField.getStyleClass().add("text-label-primary"); //And another one for TextFields so that we maintain consistency
        copyButtonUsername.getStyleClass().add("button-primary");
        passwordField.getStyleClass().add("text-label-primary");
        editButton.getStyleClass().add("button-primary");
        deleteButton.getStyleClass().add("button-primary");
        copyButtonPassword.getStyleClass().add("button-primary");
    }
}
