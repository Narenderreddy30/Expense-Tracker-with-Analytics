import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String FILE_NAME = "expenses.txt";

    public static List<Expense> loadExpenses() {
        List<Expense> list = new ArrayList<>();

        try {
            File f = new File(FILE_NAME);
            if (!f.exists()) f.createNewFile();

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while ((line = br.readLine()) != null) {
                Expense e = Expense.fromFileString(line);
                if (e != null) list.add(e);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void saveExpenses(List<Expense> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

            for (Expense e : list) {
                bw.write(e.toFileString());
                bw.newLine();
            }

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
