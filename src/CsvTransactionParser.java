import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CsvTransactionParser implements TransactionParser {

    @Override
    public List<Transaction> parse(File file) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] contents = line.split(",");
                Transaction transaction = new Transaction();
                transaction.setDescription(contents[0]);
                transaction.setDirection(contents[1]);
                transaction.setAmount(new BigDecimal(contents[2]));
                transaction.setCurrency(contents[3]);
                transactions.add(transaction);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static void main(String[] args) {
        CsvTransactionParser parser = new CsvTransactionParser();
        File file = new File("C:\\Users\\DELL\\Desktop\\transactions.csv");
        List<Transaction> transactions = parser.parse(file);
        System.out.println("this is the CSV parser");
        transactions.forEach(System.out::println);
    }
}
