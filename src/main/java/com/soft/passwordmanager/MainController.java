package com.soft.passwordmanager;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.Duration;

public class MainController {
    @FXML
    private BorderPane root;
    @FXML
    private VBox credentialsPanel;
    @FXML
    private HBox searchBarWrapper;
    @FXML
    private HBox topListFragment;
    @FXML
    private TextField searchBar;
    @FXML
    private Button addButton;
    @FXML
    private ListView<Credentials> credentialsList;

    @FXML
    public void initialize(){
        loadView("empty-view.fxml");

        //TODO: Finish the design (make the program look pretty!).
        //TODO: handle entering text in the searchBar.
        //TODO: add functionality to the new-view (encryption).
        //TODO: add functionality to the DetailsController so that user can copy the password/username with a button.
        //TODO: add import and export functionality.
        //TODO: refactor the CSS styles to use classes normally.
        credentialsList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null){
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("details-view.fxml"));
                    Parent view = loader.load();
                    DetailsController detailsController = loader.getController();
                    detailsController.setCredentialData(newVal);
                    root.setCenter(view);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        Insets searchBarInsets = new Insets(10, 10, 10 ,10);
        searchBarWrapper.setPadding(searchBarInsets);
        addButton.setMaxHeight(Double.MAX_VALUE);
        addButton.setMaxWidth(Double.MAX_VALUE);
        credentialsList.setMaxHeight(Double.MAX_VALUE);
        topListFragment.getStyleClass().add("top-list-fragment");
        addButton.getStyleClass().add("add-button");
        searchBar.getStyleClass().add("search-bar");
        VBox.setVgrow(credentialsList, Priority.ALWAYS);
        credentialsList.getStyleClass().add("listview-credentials");
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
