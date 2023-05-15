package org.example;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class RunnableServer implements Runnable{
    private Socket clientSocket;

    private User attiveUserInThread = null;

    public RunnableServer(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    /*
    *   formazione di una stringa che posso prendere:
    *
    *   cosa_Da_FareCODEMAX##!!2ddinfo
    *
    *   es:
    *   modificaCODEMAX##!!2ddjson;
    *
    *
    *
    * */


    private boolean canModifyUser(User user){
        if(attiveUserInThread != null){
            //System.out.println("non sono piu null");

            if(attiveUserInThread.equals(user)){
                //System.out.println("ti do true perche sei quello che dico io");

                return true;
            }
            else {
                if(!Main.activeUsers.contains(user.toJson())){
                    //System.out.println("ok è un nuovo utnete che pero nn è nell'arraylist");

                    Main.activeUsers.remove(attiveUserInThread.toJson());
                    attiveUserInThread = user;
                    return true;
                }
                else {
                    //System.out.println("emm tu gia cisei nell'array list");
                    return false;
                }
            }
        }
        else {
            //System.out.println("sono entrato nell'else");

            if(!Main.activeUsers.contains(user.toJson())){
                attiveUserInThread = user;
                Main.activeUsers.add(attiveUserInThread.toJson());


                //System.out.println("ho aggiungo" + user.getUserName()+"#"+user.getTag());


                return true;
            }
            else {
                //System.out.println("gia ci stqai");
                return false;
            }
        }
    }

    @Override
    public void run() {
        //applica qui la logiaca del server.


        try {
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            while (true){
                String x = in.readLine();

                if(x.equals("exit")){
                    System.out.println("chiudo la connessione con il client");

                    if(attiveUserInThread != null){
                        Main.activeUsers.remove(attiveUserInThread.toJson());
                    }

                    clientSocket.close();
                    return;
                }

                String cosaDaFare = x.split("CODEMAX##!!2dd")[0];


                if(cosaDaFare.equals("write")){
                    String json = x.split("CODEMAX##!!2dd")[1];

                    User user = new User();
                    user.generateFromJson(json);

                    if(canModifyUser(user)){ //in teoria sempre vero..
                        user.save();
                    }

                    out.writeBytes("");
                }
                else if(cosaDaFare.equals("get")){
                    String path = x.split("CODEMAX##!!2dd")[1];
                    String userDescription = "null\n";

                    //System.out.println(new File("./jsonsStorage/"+path+".json").getAbsolutePath());
                    if(new File("./jsonsStorage/"+path+".json").exists()){
                        User user = new User();
                        user.load("./jsonsStorage/"+path+".json");

                        //System.out.println("vedo se posso modificare l'utente");

                        if(canModifyUser(user)){
                            userDescription = user.toJson() + "\n";
                        }
                    }




                    out.writeBytes(userDescription);
                }
                else if(cosaDaFare.equals("setNewUser")){
                    String json = x.split("CODEMAX##!!2dd")[1];
                    String path = x.split("CODEMAX##!!2dd")[2];
                    String userDescription = "null\n";


                    File file = new File("./jsonsStorage/"+path+".json");
                    if(file.exists()){
                        User Olduser = new User();
                        Olduser.load("./jsonsStorage/"+path+".json");

                        //user con le cose nuove
                        User NewUser = new User();
                        NewUser.generateFromJson(json);

                        if(canModifyUser(Olduser)){
                            Olduser.setTag(NewUser.getTag());
                            Olduser.setGeneralPassword(NewUser.getGeneralPassword());
                            Olduser.setUserName(NewUser.getUserName());

                            Main.activeUsers.remove(attiveUserInThread.toJson());
                            Main.activeUsers.add(Olduser.toJson());

                            file.delete();
                            Olduser.save();

                            userDescription = Olduser.toJson() + "\n";
                        }
                    }

                    out.writeBytes(userDescription);
                }
                else if(cosaDaFare.equals("removeUser")){
                    if(attiveUserInThread != null){
                        Main.activeUsers.remove(attiveUserInThread.toJson());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("programma client chiuso bruscamente");

            if(attiveUserInThread != null){
                Main.activeUsers.remove(attiveUserInThread.toJson());
            }

            //throw new RuntimeException(e);
        }

    }
}
