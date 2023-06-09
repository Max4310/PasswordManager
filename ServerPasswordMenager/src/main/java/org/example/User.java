package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class User {
    private ArrayList<Password> passwords;  /*nome servizio, password di quel servizio
                                                insta, (username: _max4310_,pass: ciao, othercamp: email : max4310@gmail.com ... ) */

    private String userName,tag;

    private String generalPassword;


    public boolean equals(User user){
        boolean trovato = true;

        if(user.passwords.size() == this.passwords.size()){
            for(int i=0;i<this.passwords.size();i++){
                if(!this.passwords.get(i).equals(user.passwords.get(i))){
                    trovato = false;
                    break;
                }
            }
        }
        else{
            //System.out.println("ko emm ");
            trovato = false;
        }

        //System.out.println(trovato);
        //System.out.println(this.toJson());



        return  this.userName.equals(user.userName) &&
                this.tag.equals(user.tag) &&
                this.generalPassword.equals(user.generalPassword) &&
                trovato;
    }

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
        this.passwords.add(password);
    }

    public void save() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        //System.out.println(userName + tag + generalPassword);


        objectMapper.writeValue(new File("./jsonsStorage/"+this.userName+"#"+this.tag+".json"), this);
    }

    private void generateFromUser(User user){
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
}

