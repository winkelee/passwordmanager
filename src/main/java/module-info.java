module com.soft.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.soft.passwordmanager to javafx.fxml;
    exports com.soft.passwordmanager;
}