package org.example;

import com.sun.source.tree.WhileLoopTree;

import java.io.*;
import java.net.*;

public class Server {

    private int numeroPorta = 4310; //copresa tra 1024 - 65 536
    private ServerSocket serverSocket = null;
    private Socket clientSocker = null;

    public Server(){
        try {
            System.out.println("0. apro il server");
            serverSocket = new ServerSocket(numeroPorta);


            while(true){
                System.out.println("1. aspetto un client sulla porta " + numeroPorta);
                clientSocker = serverSocket.accept();
                System.out.println("2. connessione stabilita con un client");

                Runnable code = new RunnableServer(clientSocker);
                new Thread(code).start();
            }


            /*in = new DataInputStream(clientSocker.getInputStream());
            out = new DataOutputStream(clientSocker.getOutputStream());*/



        } catch (IOException e) {
            System.err.println("Errore nella connessione!");
        }
    }
}
