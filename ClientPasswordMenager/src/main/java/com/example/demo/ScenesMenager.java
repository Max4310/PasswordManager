package com.example.demo;

import com.example.demo.Controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ScenesMenager {

    private Stage courrentStage;

    public ScenesMenager(Stage courrentStage) {
        this.courrentStage = courrentStage;
    }

    public void showPopUp(Popup popup){
        popup.show(courrentStage);
    }

    public Controller changeScene(String path, String name){
        try {
            //ystem.out.println(new File("src\\main\\resources\\com\\example\\demo\\"+path).getAbsolutePath());
            FXMLLoader fxmlLoader = new FXMLLoader(new File("src\\main\\resources\\com\\example\\demo\\"+path).toURI().toURL());

            Scene scene = new Scene(fxmlLoader.load());

            Controller controller = null;
            try{
                controller = fxmlLoader.getController();
                controller.setScenesMenager(this);
            }
            catch (Exception e){
                System.err.println("errore nel controller");
            }

            courrentStage.setTitle(name);
            courrentStage.setScene(scene);
            courrentStage.setResizable(false);

            courrentStage.show();

            return controller;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stage getCourrentStage() {
        return courrentStage;
    }
}
