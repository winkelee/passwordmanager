package com.soft.passwordmanager;

import com.soft.passwordmanager.Credentials;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;

public class CredentialCell extends ListCell<Credentials> {
    private Label URLLabel;
    private Label usernameLabel;
    private VBox layout;

    public CredentialCell(){
        URLLabel = new Label();
        usernameLabel = new Label();
        layout = new VBox(5);
        layout.getChildren().addAll(URLLabel, usernameLabel);
        usernameLabel.getStyleClass().add("list-label-secondary");
        URLLabel.getStyleClass().add("list-label-primary");
        layout.setMaxWidth(Double.MAX_VALUE);
        URLLabel.setMaxWidth(Double.MAX_VALUE);
        usernameLabel.setMaxWidth(Double.MAX_VALUE);
    }

    @Override
    protected void updateItem(Credentials credentials, boolean empty){
        super.updateItem(credentials, empty);
        if (empty || credentials == null){
            setGraphic(null);
        } else {
            usernameLabel.setText(credentials.getUsername());
            URLLabel.setText(credentials.getHostUrl());
            setGraphic(layout);
            getStyleClass().add("list-cell");
        }
    }

}
