import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlTransactionParser implements TransactionParser {

    @Override
    public List<Transaction> parse(File file) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Transaction");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String description = element.getElementsByTagName("Description").item(0).getTextContent();
                    String direction = element.getElementsByTagName("Direction").item(0).getTextContent();
                    BigDecimal amount = new BigDecimal(element.getElementsByTagName("Value").item(0).getTextContent());
                    String currency = element.getElementsByTagName("Currency").item(0).getTextContent();

                    Transaction transaction = new Transaction();
                    transaction.setDescription(description);
                    transaction.setDirection(direction);
                    transaction.setAmount(amount);
                    transaction.setCurrency(currency);

                    transactions.add(transaction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static void main(String[] args) {
        XmlTransactionParser parser = new XmlTransactionParser();
        File file = new File("C:\\Users\\DELL\\Desktop\\transactions.xml");
        List<Transaction> transactions = parser.parse(file);
        System.out.println("this is the XML parser");
        transactions.forEach(System.out::println);
    }
}
