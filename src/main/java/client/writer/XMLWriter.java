package client.writer;

/**
 * Created by Dotin school 6 on 1/23/2015.
 */
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import client.Terminal;
import client.model.Transaction;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLWriter {

    public void writexml() {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();


            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("transactions");
            doc.appendChild(rootElement);

            for(Transaction transaction : Terminal.transactions){
                if(transaction.getRemainingBalance().compareTo(BigDecimal.ZERO) == 1){
                    Element transElement = doc.createElement("transaction");
                    rootElement.appendChild(transElement);

                    Attr attributeID = doc.createAttribute("id");
                    attributeID.setValue(String.valueOf(transaction.getId()));
                    transElement.setAttributeNode(attributeID);

                    Attr attributeAccount = doc.createAttribute("account");
                    attributeAccount.setValue(transaction.getAccount());
                    transElement.setAttributeNode(attributeAccount);

                    Attr attributeBalance = doc.createAttribute("RemainingBalance");
                    attributeBalance.setValue(transaction.getRemainingBalance().toString());
                    transElement.setAttributeNode(attributeBalance);
                }
            }










            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new  File("src/main/java/client/Response.xml").getAbsolutePath());



            transformer.transform(source, result);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
