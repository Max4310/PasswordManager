package com.example.demo.Controllers;

import com.example.demo.HelloApplication;
import com.example.demo.Password;
import com.example.demo.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FXhomePageController extends Controller{

    public static FXhomePageController controllerHomeScene;

    @FXML
    private MenuItem MENUITEMModificaPassword;
    @FXML
    private MenuItem MENUITEMCopiaPassword;
    @FXML
    private MenuItem MENUITEMCopiaUserName;


    @FXML private TableColumn colCopyUser;
    @FXML private TableColumn colCopyPassword;



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


    public static Popup popup;


    public static Password password;


    private void setMENUPASSWORDDisable(boolean x){
        MENUITEMCopiaPassword.setDisable(x);
        MENUITEMCopiaUserName.setDisable(x);
        MENUITEMModificaPassword.setDisable(x);
    }

    public void initialize() {
        controllerHomeScene = this;


        try {
            popup = new Popup();

            FXMLLoader fxmlLoader = new FXMLLoader(new File("src\\main\\resources\\com\\example\\demo\\PopUp.fxml").toURI().toURL());

            VBox hBoxPopUp = fxmlLoader.load();
            popup.getContent().add(hBoxPopUp);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        KeyCodeCombination copyKeyCodeCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
        //aggiungo un evento "ogni volta che viene clicato un tasto sulla tastiera e ho selezionato una riga della tabella"
        tblPasswords.setOnKeyPressed(event -> {
            //i tasti che ho premuto coincidono con "control c?"
            if (copyKeyCodeCombination.match(event)) {
                //prendi la lista delle cose che sono selezionate
                ObservableList<Password> selectedPasswords = tblPasswords.getSelectionModel().getSelectedItems();
                Password password = selectedPasswords.get(0); //prendo la prima cosa selezionata

                HelloApplication.copiaInAppunti_SO("userName: " + password.getUserName() + "\npassword: " +  password.getPassword());

                event.consume(); //gli dico che è finito l'evento
            }
        });

        tblPasswords.setOnMouseClicked(event -> {
            popup.hide();

            if(tblPasswords.getSelectionModel().getSelectedItems().size()>0){
                password = (Password) tblPasswords.getSelectionModel().getSelectedItems().get(0);

                setMENUPASSWORDDisable(false);

                event.consume();
            }

            //è il click destro?
            if(event.getButton() == MouseButton.SECONDARY && tblPasswords.getSelectionModel().getSelectedItems().size()>0){
                popup.setX(event.getScreenX());
                popup.setY(event.getScreenY());

                HelloApplication.scenesMenager.showPopUp(popup);

                tblPasswords.getSelectionModel().clearSelection();
                setMENUPASSWORDDisable(true);


                event.consume();
            }
        });

        colWebsite.setCellValueFactory(new PropertyValueFactory<>("nomeServizio"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));


        /*colPassword.setCellValueFactory(new PropertyValueFactory<>("copyUsername"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("copyPassword"));*/

        tblPasswords.setItems(FXCollections.observableArrayList());
        //tblPasswords.getColumns().addAll(colWebsite, colUsername, colPassword);

        reloadObservableArrayList(HelloApplication.user.getPasswords());
        setMENUPASSWORDDisable(true);
    }


    public void reloadObservableArrayList(ArrayList<Password> passwords){
        //System.out.println(HelloApplication.user.getUserName());
        UserLabel.setText("Bentornato " + HelloApplication.user.getUserName()+"#"+HelloApplication.user.getTag());
        colCopyUser.setCellFactory(tc -> new TableCell<Password, String>() {

            private Password passwordRow;
            private final Button button = new Button("Copia!");
            {
                button.setOnAction(event -> {
                    ClipboardContent content = new ClipboardContent(); //creo questo oggetto e dentro di metto le cose che voglio copiare
                    content.putString(passwordRow.getUserName());
                    Clipboard.getSystemClipboard().setContent(content); //Aggiungo la stringa agli "appunti" del S.O. dell'utilizzatore
                });
            }

            @Override //metodo obbligatorio per customizzare il pulsante..
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    passwordRow = getTableView().getItems().get(getIndex());
                    button.setPrefWidth(100.0);
                    setGraphic(button);
                }
            }
        });

        colCopyPassword.setCellFactory(tc -> new TableCell<Password, String>() {

            private Password passwordRow;
            private final Button button = new Button("Copia!");
            {
                button.setOnAction(event -> {
                    ClipboardContent content = new ClipboardContent(); //creo questo oggetto e dentro di metto le cose che voglio copiare
                    content.putString(passwordRow.getPassword());
                    Clipboard.getSystemClipboard().setContent(content); //Aggiungo la stringa agli "appunti" del S.O. dell'utilizzatore
                });
            }

            @Override //metodo obbligatorio per customizzare il pulsante..
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    button.setPrefWidth(100.0);
                    passwordRow = getTableView().getItems().get(getIndex());


                    setGraphic(button);
                }
            }
        });


        ObservableList<Password> passwordList = FXCollections.observableArrayList(passwords);
        tblPasswords.setItems(passwordList);
    }

    public void OnSearchAction(ActionEvent actionEvent) {
        //clicato il coso della ricerca

        if(txtSearch.getText().equals("")){ //se è vuoto
            reloadObservableArrayList(HelloApplication.user.getPasswords());
            riferimentiSearch.setText("");
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
        User target = HelloApplication.user;
        HelloApplication.user.generateFromUser(
                HelloApplication.serverReference.getUserFromServer(target.getUserName()+"#"+target.getTag()));
        HelloApplication.scenesMenager.changeScene("FxaddPassword.fxml" ,"Aggiungi Password");
    }

    public void OnRicarica(ActionEvent actionEvent) {
        User target = HelloApplication.user;
        HelloApplication.user.generateFromUser(
                HelloApplication.serverReference.getUserFromServer(target.getUserName()+"#"+target.getTag()));
        reloadObservableArrayList(target.getPasswords());
    }

    public void OnModificaUser(ActionEvent actionEvent) {
        HelloApplication.scenesMenager.changeScene("FXModificaUtente.fxml","");
    }

    public void OnLogOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.scenesMenager.changeScene("FXlogin.fxml", "Login");
        HelloApplication.saveScene();

        HelloApplication.serverReference.removeUserInServerMemory();
    }

    public void OnModificaPassword(ActionEvent actionEvent) {
        tblPasswords.getSelectionModel().clearSelection();
        setMENUPASSWORDDisable(true);

        HelloApplication.scenesMenager.changeScene("FXModificaPassword.fxml", "Modifica");
    }

    public void OnCopiaPassword(ActionEvent actionEvent) {
        tblPasswords.getSelectionModel().clearSelection();
        setMENUPASSWORDDisable(true);

        HelloApplication.copiaInAppunti_SO(password.getPassword());
    }

    public void OnCopiaUserName(ActionEvent actionEvent) {
        tblPasswords.getSelectionModel().clearSelection();
        setMENUPASSWORDDisable(true);


        HelloApplication.copiaInAppunti_SO(password.getUserName());
    }
}
