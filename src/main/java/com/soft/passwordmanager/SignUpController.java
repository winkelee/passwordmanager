package com.soft.passwordmanager;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class SignUpController {
    @FXML
    private AnchorPane baseLayout;
    @FXML
    private Label signupLabelOne;
    @FXML
    private Label signupLabelTwo;
    @FXML
    private TextField masterPasswordField;
    @FXML
    private Button addButton;

    @FXML
    public void initialize(){
        baseLayout.getStyleClass().add("base-layout");
        signupLabelOne.getStyleClass().add("text-label-primary");
        signupLabelTwo.getStyleClass().add("text-label-primary");
        masterPasswordField.getStyleClass().add("text-field-primary");
        addButton.getStyleClass().add("button-primary");

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }

}
