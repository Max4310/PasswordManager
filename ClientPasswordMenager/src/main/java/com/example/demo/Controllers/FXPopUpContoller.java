package com.example.demo.Controllers;

import com.example.demo.HelloApplication;
import javafx.event.ActionEvent;

public class FXPopUpContoller extends Controller{

    public void OnCopiaUsername(ActionEvent actionEvent) {
        HelloApplication.copiaInAppunti_SO(FXhomePageController.password.getUserName());
        FXhomePageController.popup.hide();
    }

    public void OnCopiaPassword(ActionEvent actionEvent) {
        HelloApplication.copiaInAppunti_SO(FXhomePageController.password.getPassword());
        FXhomePageController.popup.hide();
    }

    public void OnEliminaRiga(ActionEvent actionEvent) {
        HelloApplication.user.removePasswordInId(FXhomePageController.password.getId());
        HelloApplication.serverReference.writeUserInServer(HelloApplication.user);
        FXhomePageController.controllerHomeScene.reloadObservableArrayList(HelloApplication.user.getPasswords());
        FXhomePageController.popup.hide();
    }

    public void OnModificaRiga(ActionEvent actionEvent) {
        HelloApplication.scenesMenager.changeScene("FXModificaPassword.fxml", "Modifica");
        FXhomePageController.popup.hide();
    }
}
