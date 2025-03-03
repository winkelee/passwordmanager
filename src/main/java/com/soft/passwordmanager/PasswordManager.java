package com.soft.passwordmanager;

import com.soft.passwordmanager.out.com.soft.passwordmanager.CredentialCell;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class PasswordManager extends Application {

    Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_view.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view, 300, 600);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(900);
        primaryStage.setScene(scene);
        primaryStage.show();
        MainController mainController = loader.getController(); //Getting an instance of a controller that is defined in the FXML file

        ObservableList<Credentials> credentialsTest = FXCollections.observableArrayList(
                new Credentials("username1", "password1", "example1.com"),
                new Credentials("username2", "password2", "example2.com"),
                new Credentials("username3", "password3", "example3.com")
        );
        mainController.setItemsInListView(credentialsTest);
        mainController.initialize();
    }

    public static void main(String[] args){
        launch();
    }

}
