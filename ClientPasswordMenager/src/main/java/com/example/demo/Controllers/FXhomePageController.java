package com.example.demo.Controllers;

import com.example.demo.HelloApplication;
import com.example.demo.Password;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;

import java.util.ArrayList;

public class FXhomePageController extends Controller{


    @FXML
    private Label riferimentiSearch;
    @FXML
    private Label UserLabel;
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView tblPasswords;
    @FXML
    private TableColumn<String, String> colWebsite;
    @FXML
    private TableColumn<String, String>  colUsername;
    @FXML
    private TableColumn<String, String>  colPassword;



    public void initialize() {
        KeyCodeCombination copyKeyCodeCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
        //aggiungo un evento "ogni volta che viene clicato un tasto sulla tastiera e ho selezionato una riga della tabella"
        tblPasswords.setOnKeyPressed(event -> {
            //i tasti che ho premuto coincidono con "control c?"
            if (copyKeyCodeCombination.match(event)) {
                //prendi la lista delle cose che sono selezionate
                ObservableList<Password> selectedPasswords = tblPasswords.getSelectionModel().getSelectedItems();
                Password password = selectedPasswords.get(0); //prendo la prima cosa selezionata


                ClipboardContent content = new ClipboardContent(); //creo questo oggetto e dentro di metto le cose che voglio copiare
                content.putString("userName: " + password.getUserName() + "\npassword: " +  password.getPassword());
                Clipboard.getSystemClipboard().setContent(content); //Aggiungo la stringa agli "appunti" del S.O. dell'utilizzatore

                event.consume(); //gli dico che è finito l'evento
            }
        });

        colWebsite.setCellValueFactory(new PropertyValueFactory<>("nomeServizio"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        tblPasswords.setItems(FXCollections.observableArrayList());
        //tblPasswords.getColumns().addAll(colWebsite, colUsername, colPassword);


        UserLabel.setText("Bentornato " + HelloApplication.user.getUserName()+"#"+HelloApplication.user.getTag());
        reloadObservableArrayList(HelloApplication.user.getPasswords());
    }


    private void reloadObservableArrayList(ArrayList<Password> passwords){
        ObservableList<Password> passwordList = FXCollections.observableArrayList(passwords);
        tblPasswords.setItems(passwordList);
    }

    public void OnSearchAction(ActionEvent actionEvent) {
        //clicato il coso della ricerca

        if(txtSearch.getText().equals("")){ //se è vuoto
            reloadObservableArrayList(HelloApplication.user.getPasswords());
        }
        else{ //se nn è vuoto

            ArrayList<Password> passwords = HelloApplication.user.getAllPasswordInAName(txtSearch.getText());

            if(passwords.size()>0){
                riferimentiSearch.setText("Ho Trovato " + passwords.size() + " Riferimenti");
                reloadObservableArrayList(passwords);
            }
            else {
                riferimentiSearch.setText("Non Ho Trovato Riferimenti");
            }

        }

    }

    public void OnAddPassword(ActionEvent actionEvent) {
        FXaddPassword controller = (FXaddPassword)  HelloApplication.scenesMenager.changeScene("FxaddPassword.fxml" ,"Aggiungi Password");
    }
}
