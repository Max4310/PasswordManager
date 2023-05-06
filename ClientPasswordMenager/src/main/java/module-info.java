module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    //requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.Controllers;
    opens com.example.demo.Controllers to javafx.fxml;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

}