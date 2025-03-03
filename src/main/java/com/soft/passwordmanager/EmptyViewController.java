package com.soft.passwordmanager;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class EmptyViewController {
    @FXML
    private Label emptyText;

    @FXML
    private HBox layout;
    @FXML
    private AnchorPane baseLayout;

    @FXML
    public void initialize(){ //This runs after all the FXML components are loaded. DO NOT USE A CONSTRUCTOR.
        Insets layoutInsets = new Insets(20, 20, 20 ,20);
        layout.setPadding(layoutInsets);
        baseLayout.getStyleClass().add("base-layout-empty");
        emptyText.getStyleClass().add("empty-text-label");

    }



}
