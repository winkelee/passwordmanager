package com.soft.passwordmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane root;
    @FXML
    private VBox credentialsPanel;
    @FXML
    private TextField searchBar;
    @FXML
    private Button addButton;
    @FXML
    private ListView<Credentials> credentialsList;

    @FXML
    public void initialize(){
        loadView("empty-view.fxml");

        //TODO: handle clicks on the ListView
        //TODO: implement populating the Listview with the custom objects.
        //TODO: handle clicks on the addButton
        //TODO: handle entering text in the searchBar
        credentialsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null){
                loadView("details-view.fxml");
            }
        });
    }

    public void loadView(String fxmlFile){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent view = loader.load();
            root.setCenter(view);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
