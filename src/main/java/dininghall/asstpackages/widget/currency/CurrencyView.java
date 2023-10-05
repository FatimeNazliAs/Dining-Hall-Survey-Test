package dininghall.asstpackages.widget.currency;

import lombok.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "currencyView")
@RequestScoped
@Data
public class CurrencyView implements Serializable {
    private List<CurrencyData> currencyDataList;

    public CurrencyView() {
        currencyDataList = new ArrayList<>();
    }

    public void updateCurrencyList() {
        currencyDataList = new ArrayList<>();

        try {
            // Create a DocumentBuilderFactory and a DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Retrieve the XML data from the website
            Document doc = builder.parse("https://www.tcmb.gov.tr/kurlar/today.xml");

            // Get the list of "Currency" elements
            NodeList currencyNodeList = doc.getElementsByTagName("Currency");

            // Iterate through the list of currencies
            for (int i = 0; i < currencyNodeList.getLength(); i++) {
                Node currencyNode = currencyNodeList.item(i);
                if (currencyNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element currencyElement = (Element) currencyNode;

                    // Get the "Isim" (name) and "ForexBuying" (buying rate) of the currency
                    String name = currencyElement.getElementsByTagName("Isim").item(0).getTextContent();
                    String buyingRate = currencyElement.getElementsByTagName("ForexBuying").item(0).getTextContent();

                    switch (name) {
                        case "ABD DOLARI":
                        case "İNGİLİZ STERLİNİ":
                        case "EURO":
                        case "KANADA DOLARI":
                        case "RUS RUBLESİ":
                        case "AVUSTRALYA DOLARI":
                            addCurrencyToList(name, buyingRate);
                            break;
                    }

                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }


    }

    private void addCurrencyToList(String name, String buyingRate) {

        CurrencyData currencyData = new CurrencyData();
        currencyData.setCurrencyName(name);
        currencyData.setCurrencyBuyingPrice(Float.parseFloat(buyingRate));
        // currencyData.setCurrencySellingPrice(Float.parseFloat(sellingRate));

        currencyDataList.add(currencyData);

    }


}