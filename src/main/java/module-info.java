module hu.vasvari {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.lang3;
    requires java.persistence;

    opens hu.vasvari.controller to javafx.fxml;
    opens hu.vasvari.model to javafx.base;
    exports hu.vasvari;
}