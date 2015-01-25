package server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
/**
 * Created by Dotin school 6 on 1/23/2015.
 */

public class ClientHandler extends Thread {
    private Socket socket;
    private JSONObject objectOut;

    public ClientHandler(Socket socket){
        this.socket=socket;

    }

    @Override
    public void run() {
        DataInputStream in ;
        JSONObject objectIn;
        JSONArray jsonArrayOut;
        JSONParser parser;
        JSONArray jsonArrayIn;
        TransactionExecutor transactionExecutor;
        try {
            in = new DataInputStream(socket.getInputStream());
            jsonArrayOut = new JSONArray();
            String input = in.readUTF();
            System.out.println(input);
            parser = new JSONParser();
            jsonArrayIn = (JSONArray) parser.parse(input);


            for (Object object : jsonArrayIn) {
                objectIn = (JSONObject) object;
                transactionExecutor = new TransactionExecutor();
                String amount = (String) objectIn.get("amount");
                String id = (String) objectIn.get("id");
                String type = (String) objectIn.get("type");
                String accountId = (String) objectIn.get("account");

                if(type.equalsIgnoreCase("deposit")) {
                    objectOut = transactionExecutor.deposit(accountId, new BigDecimal(amount));
                    objectOut.put("id", id);
                }else if(type.equalsIgnoreCase("withdraw")){
                    objectOut = transactionExecutor.withdraw(accountId, new BigDecimal(amount));
                    objectOut.put("id", id);
                }else {
                    String resultMsg = "invalid type for transaction";
                    String balance = "-1";
                    objectOut = new JSONObject();
                    objectOut.put("id", id);
                    objectOut.put("result", resultMsg);
                    objectOut.put("Balance", balance);

                }

                jsonArrayOut.add(objectOut);
            }
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(jsonArrayOut.toJSONString());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
