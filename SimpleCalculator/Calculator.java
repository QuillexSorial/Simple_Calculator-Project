import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Input_Handler inputHandler = new Input_Handler();
        Operations operations = new Operations();
        Display display = new Display();
        ErrorHandler errorhandler = new ErrorHandler();
        HistoryManager history = new HistoryManager();

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            // Show options menu
            System.out.println("\n==== Calculator Menu ====");
            System.out.println("1. Perform Calculation");
            System.out.println("2. Show History");
            System.out.println("3. Clear History");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    double num1 = inputHandler.getNumber();
                    char operator = inputHandler.getOperator();
                    double num2 = inputHandler.getNumber();

                    try {
                        double result = switch (operator) {
                            case '+' -> operations.add(num1, num2);
                            case '-' -> operations.subtract(num1, num2);
                            case '*' -> operations.multiply(num1, num2);
                            case '/' -> operations.divide(num1, num2);
                            default -> throw new IllegalStateException("Unexpected operator: " + operator);
                        };

                        display.showResult(num1, num2, operator, result);
                        history.addRecord(num1, num2, operator, result);
                    } catch (Exception e) {
                        errorhandler.handleError(e);
                    }
                }
                case 2 -> history.showHistory();
                case 3 -> history.clearHistory();
                case 4 -> {
                    System.out.println("Goodbye!");
                    keepRunning = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
