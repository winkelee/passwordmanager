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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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

        //TODO: handle entering text in the searchBar
        //TODO: add functionality to the new-view (encryption).
        //TODO: add functionality to the DetailsController so that user can copy the password/username with a button.
        //TODO: add import and export functionality.
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

        credentialsList.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.equals(KeyCode.ENTER)){

            }
        }
    });
        Insets searchBarInsets = new Insets(10, 10, 10 ,10);
        searchBarWrapper.setPadding(searchBarInsets);
        addButton.setMaxHeight(Double.MAX_VALUE);
        addButton.setMaxWidth(Double.MAX_VALUE);
        credentialsList.setMaxHeight(Double.MAX_VALUE);
        topListFragment.getStyleClass().add("base-layout"); //the hand speaks the hand of a government man well im a tumbler born underpunches im so thin
        addButton.getStyleClass().add("button-primary");
        searchBar.getStyleClass().add("text-field-primary");
        VBox.setVgrow(credentialsList, Priority.ALWAYS);
        credentialsList.getStyleClass().add("listview-credentials");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadView("new-view.fxml");
            }
        });
    }

    public List<Credentials> lookForDomain(String string, List<Credentials> targetList){
        ArrayList<Credentials> resultList = new ArrayList<Credentials>();
        for (int i = 0; i < targetList.size(); i++){
            if (targetList.get(i).getHostUrl() == string){
                resultList.add(targetList.get(i));
            }
        }
        return  resultList;
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
