package com.example.demo;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class HelloApplication extends Application {


    //uso una logica singleton per queste informazioni che sono comuni a tutto il programma
    public static User user;
    public static boolean ricordami;
    public static ScenesMenager scenesMenager;
    public static Client serverReference;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FXlogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scenesMenager = new ScenesMenager(stage);



        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        serverReference = new Client();
        launch();

        File directory = new File("./jsonsStorage");
        for (File file : directory.listFiles()){
            if(file.exists()){
                file.delete();
            }
        }

        if(user != null){
            if(ricordami){
                user.save();
            }

            serverReference.writeUserInServer(user);
        }

        serverReference.closeConnection();

    }

    public static void copiaInAppunti_SO(String string){
        ClipboardContent content = new ClipboardContent(); //creo questo oggetto e dentro di metto le cose che voglio copiare
        content.putString(string);
        Clipboard.getSystemClipboard().setContent(content); //Aggiungo la stringa agli "appunti" del S.O. dell'utilizzatore
    }


}