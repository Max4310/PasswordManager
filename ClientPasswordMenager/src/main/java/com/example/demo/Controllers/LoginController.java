package com.example.demo.Controllers;

import com.example.demo.HelloApplication;
import com.example.demo.User;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;


public class LoginController extends Controller {

    @FXML
    private CheckBox termsCheckbox;
    @FXML
    private Label errorText; //visualizzare gli errori nel inserimento dei dati

    @FXML
    private TextField user;
    @FXML
    private PasswordField password;
    @FXML
    private TextField tag;

    private void printError(String error){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(1500);
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            errorText.setText("");
        });

        if(! errorText.getText().equals(error)){
            errorText.setText(error);
            new Thread(task).start();
        }
    }
    public void pressLogInButton(ActionEvent actionEvent) throws IOException {

        if(user.getText().equals("") || password.getText().equals("") || tag.getText().equals("")) {
            printError("Inserisci tutti i campi");
        }
        else{
            String fileDirection = "./jsonsStorage/"+ user.getText() + "#" + tag.getText() + ".json";
            User target = null;


            if(new File(fileDirection).exists()){
                target = new User(user.getText(), tag.getText());
                target.load(fileDirection);
            }
            else if(HelloApplication.serverReference.exitstUser(user.getText()+"#"+tag.getText())){
                target = HelloApplication.serverReference.getUserFromServer(user.getText()+"#"+tag.getText());
            }

            if(target != null){
                if(target.getGeneralPassword().equals(password.getText())){
                    HelloApplication.user = target;
                    HelloApplication.ricordami = termsCheckbox.isSelected();

                    Controller controller = HelloApplication.scenesMenager.changeScene("FXhomePage.fxml" ,"");
                    FXhomePageController controller1 = (FXhomePageController) controller;

                    controller1.initialize();
                }
                else{
                    printError("Password Errata");
                }
            }
            else {
                printError("Parametri Errati");
            }
        }
    }

    public void pressSingInButton(ActionEvent actionEvent) {
        //load scene in fxsingin.fxml

        HelloApplication.scenesMenager.changeScene("FXsingIn.fxml","Registrati");
    }
}

