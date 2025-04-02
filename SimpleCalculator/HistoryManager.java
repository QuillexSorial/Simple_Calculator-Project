import java.util.ArrayList;

public class HistoryManager {
    private ArrayList<String> history = new ArrayList<>();

    public void addRecord(double num1, double num2, char operator, double result) {
        String record = num1 + " " + operator + " " + num2 + " = " + result;
        history.add(record);
    }

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No history yet.");
        } else {
            System.out.println("\nCalculation History:");
            for (String entry : history) {
                System.out.println(entry);
            }
        }
    }

    public void clearHistory() {
        history.clear();
        System.out.println("History cleared.");
    }

    public ArrayList<String> getHistory() {
        return history;
    }
    
}
