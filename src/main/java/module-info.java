module com.ciber.javafxcrud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.ciber.javafxcrud to javafx.fxml;
    exports com.ciber.javafxcrud;
    exports com.ciber.javafxcrud.database;
    exports com.ciber.javafxcrud.model;
}
