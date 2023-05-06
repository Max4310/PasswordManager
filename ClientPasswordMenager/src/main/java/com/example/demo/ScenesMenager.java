package com.example.demo;

import com.example.demo.Controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScenesMenager {

    private Stage courrentStage;

    public ScenesMenager(Stage courrentStage) {
        this.courrentStage = courrentStage;
    }

    public Controller changeScene(String path, String name){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ScenesMenager.class.getResource(path));

            Scene scene = new Scene(fxmlLoader.load());

            Controller controller = fxmlLoader.getController();
            controller.setScenesMenager(this);

            courrentStage.setTitle(name);
            courrentStage.setScene(scene);
            courrentStage.setResizable(false);

            courrentStage.show();

            return controller;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
