package com.example.demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
    private int numeroPorta = 4310; //copresa tra 1024 - 65 536
    private Socket clientSocker = null;

    public Client (){
        try {
            System.out.println("0. provo a connettermi al server");
            clientSocker = new Socket(InetAddress.getLocalHost(), numeroPorta);
            System.out.println("1. connesso");
        }
        catch (UnknownHostException e){
            System.err.println("Errore di host sconosciuto");
        }
        catch (IOException e) {
            System.err.println("Errore nella connessione al server");
        }
    }

    public void writeUserInServer(User user){
        try {
            DataOutputStream out = new DataOutputStream(clientSocker.getOutputStream());
            String userJson = "writeCODEMAX##!!2dd"+user.toJson()+"\n";
            out.writeBytes(userJson);


            System.out.println("user mandato");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(){
        try {
            DataOutputStream out = new DataOutputStream(clientSocker.getOutputStream());
            out.writeBytes("exit\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exitstUser(String path){
        return getUserFromServer(path) != null;
    }

    public User getUserFromServer(String path){
        try {
            DataOutputStream out = new DataOutputStream(clientSocker.getOutputStream());
            DataInputStream in = new DataInputStream(clientSocker.getInputStream());
            String serverInfo ="getCODEMAX##!!2dd"+ path + "\n";
            out.writeBytes(serverInfo);
            System.out.println("path inviato");

            String userString = in.readLine();
            User user = null;

            if(!userString.equals("null")){
                user = new User();
                user.generateFromJson(userString);

                System.out.println("user ricevuto");
            }


            return user;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public User setNewUserInServer(String path, User newUser){

        try {
            DataOutputStream out = new DataOutputStream(clientSocker.getOutputStream());
            DataInputStream in = new DataInputStream(clientSocker.getInputStream());

            String UserAndPath = "setNewUserCODEMAX##!!2dd"+newUser.toJson()+"writeCODEMAX##!!2dd"+path+"\n";
            out.writeBytes(UserAndPath);

            System.out.println("user e path mandati");

            String userString = in.readLine();
            User user = null;

            if(!userString.equals("null")){
                user = new User();
                user.generateFromJson(userString);

                System.out.println("user ricevuto");
            }


            return user;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserInServerMemory(){
        try {
            DataOutputStream out = new DataOutputStream(clientSocker.getOutputStream());
            out.writeBytes("removeUserCODEMAX##!!2dd");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
