package com.soft.passwordmanager;

import javafx.application.Application;
import javafx.stage.Stage;

public class PasswordManager extends Application {
    Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setWidth(500);
        primaryStage.setHeight(300);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
