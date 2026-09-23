import java.util.*;

public class ExpenseManager {
    private List<Expense> expenses;

    public ExpenseManager() {
        expenses = FileHandler.loadExpenses();
    }

    public List<Expense> getAllExpenses() {
        return expenses;
    }

    public void addExpense(Expense e) {
        expenses.add(e);
        FileHandler.saveExpenses(expenses);
    }

    public void deleteExpense(int index) {
        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            FileHandler.saveExpenses(expenses);
        }
    }

    public double getTotalExpense() {
        double total = 0;
        for (Expense e : expenses)
            total += e.getAmount();
        return total;
    }

    public Map<String, Double> getCategoryTotals() {
        Map<String, Double> map = new HashMap<>();

        for (Expense e : expenses) {
            map.put(e.getCategory(),
                map.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
        }

        return map;
    }
    public double getTotalForPrefix(String prefix) {
        double total = 0;
        for (Expense e : expenses) {
            if (e.getDate() != null && e.getDate().startsWith(prefix)) {
                total += e.getAmount();
            }
        }
        return total;
    }
}
