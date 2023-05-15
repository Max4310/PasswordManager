package com.example.demo.Controllers;

import com.example.demo.HelloApplication;
import com.example.demo.User;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXmodificaUtente {

    @FXML
    private Label errorText;
    @FXML
    private TextField tag;
    @FXML
    private TextField user;
    @FXML
    private TextField password;

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

    public void onPressModifica(ActionEvent actionEvent) {
        System.out.println("ciao");


        String  userString = HelloApplication.user.getUserName(),
                tagString = HelloApplication.user.getTag(),
                passwordString = HelloApplication.user.getGeneralPassword();

        if(!user.getText().equals("")){
            userString = user.getText();
        }

        if(!tag.getText().equals("")){
            tagString = tag.getText();
        }

        if(!password.getText().equals("")){
            passwordString = password.getText();
        }


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
            User user1 = new User(userString,tagString,passwordString);

            if(!HelloApplication.serverReference.exitstUser(userPath)){
                User user3 = HelloApplication.serverReference.setNewUserInServer(
                        HelloApplication.user.getUserName()+"#"+HelloApplication.user.getTag(),
                        user1);

                if(user3 != null){
                    HelloApplication.user = user3;
                    FXhomePageController fXhomePageController = (FXhomePageController) HelloApplication.scenesMenager.changeScene("FxhomePage.fxml","");
                    fXhomePageController.initialize();
                }
                else{
                    printError("qualcosa è andato storto");
                }
            }
            else{
                printError("L'utente Gia Esiste");
            }
        }
        else {
            printError("Il Tag è Errato");
        }



        FXhomePageController fXhomePageController = (FXhomePageController) HelloApplication.scenesMenager.changeScene("FxhomePage.fxml","");
        fXhomePageController.initialize();
    }

    public void onPressIndietro(ActionEvent actionEvent) {
        FXhomePageController controller = (FXhomePageController) HelloApplication.scenesMenager.changeScene("FXhomePage.fxml" ,"");
        controller.initialize();
    }
}
