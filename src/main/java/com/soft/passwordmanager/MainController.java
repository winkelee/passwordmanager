package com.soft.passwordmanager;

import com.soft.passwordmanager.out.com.soft.passwordmanager.CredentialCell;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        //TODO: handle clicks on the addButton -- DONE
        //TODO: handle entering text in the searchBar
        //credentialsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
        //    if (newVal != null){
        //        loadView("details-view.fxml");
        //    }
        //});
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadView("new-view.fxml");
            }
        });
    }

    public void setItemsInListView(ObservableList<Credentials>  observableList){
        credentialsList.setItems(observableList);
        credentialsList.setCellFactory(param -> new CredentialCell());
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
