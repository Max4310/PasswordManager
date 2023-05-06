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

public class SingInController extends Controller{

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
    public void pressSingInButton(ActionEvent actionEvent) {
        if(!user.getText().equals("") && !password.getText().equals("") && !tag.getText().equals("")){
            String tagString = tag.getText();

            char[] tagArrayChar = tagString.toCharArray();
            boolean tuttiNumeri = false;

            for(char x : tagArrayChar){
                if (x >= 48 && x <= 57 ) {
                    tuttiNumeri = true;
                    break;
                }
            }

            if(tagArrayChar.length == 4 && tuttiNumeri){
                String userPath = user.getText() + "#" + tag.getText();

                if(!HelloApplication.serverReference.exitstUser(userPath)){
                    HelloApplication.user = new User(user.getText(), tag.getText(), password.getText());
                    HelloApplication.ricordami = termsCheckbox.isSelected();

                    FXhomePageController fXhomePageController = (FXhomePageController) HelloApplication.scenesMenager.changeScene("FxhomePage.fxml","");
                    fXhomePageController.initialize();
                }
                else{
                    printError("L'utente Gia Esiste");
                }
            }
            else {
                printError("Il Tag è Errato");
            }
        }
        else{
            printError("Inserisci Tutti I Campi");
        }
    }
}
