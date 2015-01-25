package server.reader;

/**
 * Created by Dotin school 6 on 1/23/2015.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.Account;

public class JSONReader {
    public ArrayList<Account> getAccounts(String address) {
        JSONParser parser = new JSONParser();
        ArrayList<Account> accounts = new ArrayList<Account>();
        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(new File("src/main/java/" + address).getAbsolutePath()));
            for (Object object : jsonArray) {
                Account account = new Account();
                JSONObject jsonObject = (JSONObject) object;

                String customer = (String) jsonObject.get("customer");
                account.setCustomer(customer);

                String id = (String) jsonObject.get("id");
                account.setId(id);

                String initialBalance = (String) jsonObject.get("initialBalance");
                account.setBalance(new BigDecimal(initialBalance));


                String upperBound = (String) jsonObject.get("upperBound");
                account.setUpperBound(new BigDecimal(upperBound));

                accounts.add(account);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}