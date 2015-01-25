package server;

import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Dotin school 6 on 1/24/2015.
 */
public class TransactionExecutor {

    private Account accountLock;

    public JSONObject deposit(String accountId, BigDecimal amount) {
        JSONObject jsonObject = new JSONObject();
        String balance;
        String resultMsg;
        for (Account account : Server.accounts) {
            if (account.getId().equalsIgnoreCase(accountId)) {
                accountLock = account;
                synchronized (accountLock){
                if ((amount.add(account.getBalance())).compareTo(account.getUpperBound()) == 1) {
                    System.out.println("Exception in UpperBound");
                    balance = "-1";
                    resultMsg = "The final Balance is greater than UpperBound Balance";
                    jsonObject.put("Balance", balance);
                    jsonObject.put("result", resultMsg);
                    Server.logServer.writeLogs(account.getId(), "Deposite Transaction" , resultMsg , account.getBalance().toString());
                } else {
                    BigDecimal vlaueOfSum = account.getBalance().add(amount);
                    System.out.println("Before Sleep..........Test Synchronize");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("After Sleep..........Test Synchronize");
                    account.setBalance(vlaueOfSum);
                    resultMsg = "The Transaction was successful";
                    balance = account.getBalance().toString();
                    jsonObject.put("Balance", balance);
                    jsonObject.put("result", resultMsg);
                    Server.logServer.writeLogs(account.getId(), "Deposit Transaction" , resultMsg , account.getBalance().toString());
                }
            }
            }
        }
        return jsonObject;
    }

    public JSONObject withdraw(String accountId, BigDecimal amount) {
        JSONObject jsonObject = new JSONObject();
        String balance;
        String resultMsg;
        for (Account account : Server.accounts) {
            if (account.getId().equalsIgnoreCase(accountId)) {
                accountLock = account;
                synchronized (accountLock) {
                    if (amount.compareTo(account.getBalance()) == 1) {
                        System.out.println("Exception in withdraw");
                        balance = "-1";
                        resultMsg = "The withdraw Amount is greater than InitialBalance";
                        jsonObject.put("Balance", balance);
                        jsonObject.put("result", resultMsg);
                        Server.logServer.writeLogs(account.getId(), "withDraw Transaction" , resultMsg , account.getBalance().toString());
                    } else {
                        account.setBalance(account.getBalance().subtract(amount));
                        balance = account.getBalance().toString();
                        resultMsg = "The Transactions was successful.";
                        jsonObject.put("Balance", balance);
                        jsonObject.put("result", resultMsg);
                        Server.logServer.writeLogs(account.getId(), "withDraw Transaction" , resultMsg , account.getBalance().toString());
                    }
                }
            }
        }
        return jsonObject;
    }
}
