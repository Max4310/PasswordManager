package org.example;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class RunnableServer implements Runnable{
    private Socket clientSocket;

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
                    clientSocket.close();
                    return;
                }

                String cosaDaFare = x.split("CODEMAX##!!2dd")[0];


                if(cosaDaFare.equals("write")){
                    String json = x.split("CODEMAX##!!2dd")[1];

                    User user = new User();
                    user.generateFromJson(json);
                    user.save();

                    out.writeBytes("");
                }
                else if(cosaDaFare.equals("get")){
                    String path = x.split("CODEMAX##!!2dd")[1];
                    String userDescription;


                    if(new File("./jsonsStorage/"+path+".json").exists()){
                        User user = new User();
                        user.load("./jsonsStorage/"+path+".json");
                        userDescription = user.toJson() + "\n";
                    }
                    else {
                        userDescription = "null\n";
                    }

                    out.writeBytes(userDescription);
                }

            }



        } catch (IOException e) {
            System.err.println("programma client chiuso bruscamente");

            //throw new RuntimeException(e);
        }

    }
}
