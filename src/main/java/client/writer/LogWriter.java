package client.writer;

import client.Terminal;
import client.model.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.TransactionExecutor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Dotin school 6 on 1/23/2015.
 */
public class LogWriter {

    public void writeLogs(String address) throws Exception {




            RandomAccessFile randomAccessFile = new RandomAccessFile(new File("src/main/java/client/"+address).getAbsolutePath(), "rw");
            for (Transaction transaction : Terminal.transactions){
                    randomAccessFile.writeBytes("id: " + transaction.getId() + ", AccountId: " + transaction.getAccount() +
                            ", Message: " + transaction.getMsg());
                    String breakLine = "\n";
                    randomAccessFile.writeBytes(breakLine);
            }

    }
}
