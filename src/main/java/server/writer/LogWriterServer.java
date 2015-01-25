package server.writer;

import server.TransactionExecutor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Created by Dotin school 6 on 1/24/2015.
 */
public class LogWriterServer {
    public RandomAccessFile randomAccessFile;
    public LogWriterServer(){
        try {
            randomAccessFile = new RandomAccessFile(new File("src/main/java/server/server.out").getAbsolutePath(), "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void writeLogs(String account, String typeTransaction, String resultMsg, String remainingBalance) {
        try {


                randomAccessFile.writeBytes("Account ID: " + account + ", " + typeTransaction + ", Result: " + resultMsg +
                ", RemainingBalance:" + remainingBalance);
                String breakLine = "\n";
                randomAccessFile.writeBytes(breakLine);

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
