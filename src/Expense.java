import java.io.Serializable;

public class Expense implements Serializable {
    private String date;
    private String category;
    private double amount;
    private String description;

    public Expense(String date, String category, double amount, String description) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public String getDate() { return date; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }

    public String toFileString() {
        return date + ";" + category + ";" + amount + ";" + description;
    }

    public static Expense fromFileString(String line) {
        String[] parts = line.split(";", -1);
        if (parts.length < 4) return null;
        return new Expense(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3]);
    }
}
