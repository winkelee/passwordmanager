module com.soft.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;


    opens com.soft.passwordmanager to javafx.fxml;
    exports com.soft.passwordmanager;
}