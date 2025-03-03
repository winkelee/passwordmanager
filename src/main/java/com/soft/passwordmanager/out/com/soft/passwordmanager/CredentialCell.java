package com.soft.passwordmanager.out.com.soft.passwordmanager;

import com.soft.passwordmanager.Credentials;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class CredentialCell extends ListCell<Credentials> {
    @FXML
    private Label URLLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private HBox layout;

    public CredentialCell(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("credentials-cell.fxml"));
           // loader.setController(this);
            layout = loader.load();
        } catch (Exception e){
            e.printStackTrace();
        }
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
        }
    }

}
