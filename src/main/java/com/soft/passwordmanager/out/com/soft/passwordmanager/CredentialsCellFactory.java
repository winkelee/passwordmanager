package com.soft.passwordmanager.out.com.soft.passwordmanager;

import com.soft.passwordmanager.Credentials;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CredentialsCellFactory implements Callback<ListView<Credentials>, ListCell<Credentials>> {
    @Override
    public ListCell<Credentials> call(ListView<Credentials> param) {
        return new ListCell<>(){
            @Override
            public void updateItem(Credentials credentials, boolean empty){
                super.updateItem(credentials, empty);
                if (empty || credentials == null){
                    setText(null);
                } else {
                    setText(credentials.getHostUrl() + " " + credentials.getUsername());
                }
            }
        };
    }
}
