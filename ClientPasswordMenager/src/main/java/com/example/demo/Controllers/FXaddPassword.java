package com.example.demo.Controllers;

import com.example.demo.HelloApplication;
import com.example.demo.Password;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FXaddPassword extends Controller{

    @FXML
    private TextField TXTuserName;
    @FXML
    private TextField TXTpassword;
    @FXML
    private TextField TXTnomeServizio;

    public void onPressAggiungi(ActionEvent actionEvent) {
        if(!TXTpassword.getText().equals("") && !TXTnomeServizio.getText().equals("") && !TXTuserName.getText().equals("")){
            Password password = new Password(TXTuserName.getText(), TXTpassword. getText(), TXTnomeServizio.getText());
            HelloApplication.user.addPassword(password);

            onPressIndietro(new ActionEvent());
        }
        else {
            TXTuserName.setText("");
            TXTpassword.setText("");
            TXTnomeServizio.setText("");

            TXTuserName.setPromptText("CAMPO OBBLIGATORIO");
            TXTpassword.setPromptText("CAMPO OBBLIGATORIO");
            TXTnomeServizio.setPromptText("CAMPO OBBLIGATORIO");
        }
    }

    public void onPressIndietro(ActionEvent actionEvent) {
        FXhomePageController controller = (FXhomePageController) HelloApplication.scenesMenager.changeScene("FXhomePage.fxml" ,"");
        controller.initialize();
    }
}
