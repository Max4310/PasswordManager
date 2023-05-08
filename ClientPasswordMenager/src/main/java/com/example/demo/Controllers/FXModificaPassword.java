package com.example.demo.Controllers;

import com.example.demo.HelloApplication;
import com.example.demo.Password;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FXModificaPassword {
    @FXML
    private TextField TXTnomeServizio;
    @FXML
    private TextField TXTuserName;
    @FXML
    private TextField TXTpassword;

    public void onPressIndietro(ActionEvent actionEvent) {
        FXhomePageController controller = (FXhomePageController) HelloApplication.scenesMenager.changeScene("FXhomePage.fxml" ,"");
        controller.initialize();
    }

    public void onPressModifica(ActionEvent actionEvent) {
        String nome = FXhomePageController.password.getNomeServizio();
        String username = FXhomePageController.password.getUserName();
        String password = FXhomePageController.password.getPassword();

        if(!TXTnomeServizio.getText().equals("")){
            nome = TXTnomeServizio.getText();
        }

        if(!TXTuserName.getText().equals("")){
            username = TXTuserName.getText();
        }

        if(!TXTpassword.getText().equals("")){
            password = TXTpassword.getText();
        }


        Password password1 = new Password(username, password, nome);
        password1.setId(FXhomePageController.password.getId());
        HelloApplication.user.editPaswordInId(FXhomePageController.password.getId(), password1);

        onPressIndietro(new ActionEvent());
    }
}
