package client;

import client.model.Transaction;
import client.reader.SettingReader;
import client.writer.LogWriter;
import client.writer.XMLWriter;
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
public class Terminal {
    public static ArrayList <Transaction> transactions;
    public static void main(String [] args)
    {
        SettingReader settingReader = new SettingReader();
        transactions = settingReader.getTransactions();
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject;
        for(Transaction transaction:transactions){
            jsonObject = new JSONObject();
            jsonObject.put("id",String.valueOf(transaction.getId()));
            jsonObject.put("type",transaction.getType());
            jsonObject.put("amount",transaction.getAmount().toString());
            jsonObject.put("account",transaction.getAccount());
            jsonArray.add(jsonObject);
        }
        try
        {
            Socket socket = new Socket(settingReader.getIp(), settingReader.getPort());
            OutputStream outToServer = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF(jsonArray.toJSONString());
            System.out.println("Terminal send Json information");
            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            String input = in.readUTF();
            System.out.println(input);
            JSONParser parser = new JSONParser();
            JSONArray jsonArrayIn = (JSONArray) parser.parse(input);
            for (Object object : jsonArrayIn) {

                JSONObject objectIn = (JSONObject) object;
                String id = (String) objectIn.get("id");
                String result = (String) objectIn.get("result");
                String balance = (String) objectIn.get("Balance");
                for(Transaction transaction : transactions){
                    if(transaction.getId() == Integer.parseInt(id)){
                        transaction.setMsg(result);
                        transaction.setRemainingBalance(new BigDecimal(balance));
                    }
                }
            }
            LogWriter logWriter = new LogWriter();
            logWriter.writeLogs(settingReader.getPathoutlog());
            XMLWriter xmlWriter = new XMLWriter();
            xmlWriter.writexml();
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}