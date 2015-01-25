package server;

/**
 * Created by Dotin school 6 on 1/23/2015.
 */
import server.reader.JSONReader;
import server.writer.LogWriterServer;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {

    public static ArrayList<Account> accounts;
    public static LogWriterServer logServer;
    public static void main(String [] args)
    {
        try {
            logServer = new LogWriterServer();
            accounts= new ArrayList<Account>();
            JSONReader jsonReader=new JSONReader();
            accounts=jsonReader.getAccounts("server/core.json");
            ServerSocket serverSocket=new ServerSocket(6666);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
                for(Account account: accounts){
                    System.out.println(account.getId() + " " + account.getBalance());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}