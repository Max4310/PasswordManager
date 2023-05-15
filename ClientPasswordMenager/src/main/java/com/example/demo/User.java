package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class User {
    private ArrayList<Password> passwords;  /*nome servizio, password di quel servizio
                                                    insta, (username: _max4310_,pass: ciao, othercamp: email : max4310@gmail.com ... ) */

    private String userName,tag;

    private String generalPassword;

    public User(){
        passwords = new ArrayList<>();
    }

    public User(String userName, String tag) {
        this.userName = userName;
        this.tag = tag;
        passwords = new ArrayList<>();
    }

    public User(String userName, String tag, String generalPassword) {
        this.userName = userName;
        this.tag = tag;
        this.generalPassword = generalPassword;
        passwords = new ArrayList<>();
    }

    public void addPassword(Password password){
        //id dell'ultimo + 1

        if(passwords.size()>0){
            password.setId(passwords.get(passwords.size()-1).getId()+1);
        }
        else {
            password.setId(0);
        }


        this.passwords.add(password);
        HelloApplication.serverReference.writeUserInServer(this);
    }

    public void save() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        objectMapper.writeValue(new File("./jsonsStorage/"+this.userName+"#"+this.tag+".json"), this);
    }

    public void generateFromUser(User user){
        if(user != null){
            this.passwords = user.passwords;
            this.generalPassword = user.generalPassword;
            this.tag = user.tag;
            this.userName = user.userName;
        }
    }

    public void load(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        User user = objectMapper.readValue(new File(path), User.class);
        generateFromUser(user);
    }

    public String getGeneralPassword() {
        return generalPassword;
    }
    public ArrayList<Password> getPasswords() {
        return passwords;
    }

    public void setPasswords(ArrayList<Password> passwords) {
        this.passwords = passwords;
    }

    public void setGeneralPassword(String generalPassword) {
        this.generalPassword = generalPassword;
    }

    public ArrayList<Password> getAllPasswordInAName(String name){
        ArrayList<Password> arrayList = new ArrayList<Password>();

        for(Password password : this.passwords){
            if(password.getNomeServizio().toLowerCase().equals(name.toLowerCase())){
                arrayList.add(password);
            }
        }

        return arrayList;
    }
    public String getUserName() {
        return userName;
    }
    public String getTag() {
        return tag;
    }

    public String toJson(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);



        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateFromJson(String json){
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(json, User.class);
            generateFromUser(user);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void removePasswordInId(int id){
        for(int i=0;i<passwords.size();i++){
            if(passwords.get(i).getId() == id){
                passwords.remove(i);
                return;
            }
        }

        HelloApplication.serverReference.writeUserInServer(this);
    }

    public void editPaswordInId(int id, Password newPassword){
        for(int i=0;i<passwords.size();i++){
            if(passwords.get(i).getId() == id){
                passwords.set(i, newPassword);
                return;
            }
        }

        HelloApplication.serverReference.writeUserInServer(this);
    }
}
