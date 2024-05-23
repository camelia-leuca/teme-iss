module com.example.proiectfinaliss {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens com.example to javafx.fxml;
    opens com.example.controllers to javafx.fxml;
    opens com.example.models to org.hibernate.orm.core, jakarta.persistence;

    exports com.example;
    exports com.example.controllers;
}
