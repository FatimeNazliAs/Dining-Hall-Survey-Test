import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class CurrencyTest
{
    @Test
    public void printCurrency()
    {
        try
        {
            // Create a DocumentBuilderFactory and a DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Retrieve the XML data from the website
            Document doc = builder.parse("https://www.tcmb.gov.tr/kurlar/today.xml");

            // Get the list of "Currency" elements
            NodeList currencyList = doc.getElementsByTagName("Currency");

            // Iterate through the list of currencies
            for (int i = 0; i < currencyList.getLength(); i++)
            {
                Node currencyNode = currencyList.item(i);
                if (currencyNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element currencyElement = (Element) currencyNode;

                    // Get the "Isim" (name) and "ForexBuying" (buying rate) of the currency
                    String name = currencyElement.getElementsByTagName("Isim").item(0).getTextContent();
                    String buyingRate = currencyElement.getElementsByTagName("ForexBuying").item(0).getTextContent();

                    // Print the name and buying rate of the currency
                    System.out.println(name + ": " + buyingRate);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e)
        {
            e.printStackTrace();
        }
    }
}
