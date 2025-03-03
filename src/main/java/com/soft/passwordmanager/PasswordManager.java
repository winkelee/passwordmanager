package com.soft.passwordmanager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        primaryStage.setMinWidth(1200);

        MainController mainController = loader.getController(); //Getting an instance of a controller that is defined in the FXML file

        ObservableList<Credentials> credentialsTest = FXCollections.observableArrayList(
                new Credentials("username1", "password1", "example1.com"),
                new Credentials("username2", "password2", "example2.com"),
                new Credentials("username3", "password3", "example3.com")
        );
        mainController.setItemsInListView(credentialsTest);
        mainController.initialize();
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch();
    }

}
