package client.reader;

import client.model.Transaction;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Dotin school 6 on 1/23/2015.
 */
public class SettingReader {

    MyHnadler myHnadler = new MyHnadler();

    public SettingReader() {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse("src/client/terminal.xml", myHnadler);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getIp() {
        return myHnadler.getIp();
    }

    public int getPort() {
        return Integer.parseInt(myHnadler.getPort());
    }

    public String getPathoutlog() {
        return myHnadler.getPathoutlog();
    }

    public ArrayList<Transaction> getTransactions() {
        return myHnadler.getTransactions();
    }

}

class MyHnadler extends DefaultHandler{
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private String ip;

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getPathoutlog() {
        return pathoutlog;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    private String port;
    private String pathoutlog;
    private boolean bserver = false;
    private boolean boutlog = false;
    private boolean btransaction = false;
    private String id;
    private String type;
    private String amount;
    private String account;
    private Transaction transaction;

    public void startElement(String uri, String localName,String qName,
                             Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("server")) {
            bserver = true;
            ip = attributes.getValue("ip");
            port = attributes.getValue("port");
        }

        if (qName.equalsIgnoreCase("outlog")) {
            boutlog = true;
            pathoutlog = attributes.getValue("path");
        }

        if (qName.equalsIgnoreCase("transaction")) {
            btransaction = true;
            transaction=new Transaction();
            id = attributes.getValue("id");
            type = attributes.getValue("type");
            amount = attributes.getValue("amount");
            account = attributes.getValue("account");
            transaction.setId(Integer.parseInt(id));
            transaction.setType(type);
            transaction.setAmount(new BigDecimal(amount));
            transaction.setAccount(account);
            transactions.add(transaction);
        }


    }

    public void endElement(String uri, String localName,
                           String qName) throws SAXException {

   }

    public void characters(char ch[], int start, int length) throws SAXException {

        if (bserver) {
            bserver = false;
        }

        if (boutlog) {
            boutlog = false;
        }

        if (btransaction) {
            btransaction = false;
        }


    }

}