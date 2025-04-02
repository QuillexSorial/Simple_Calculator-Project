import java.util.InputMismatchException;
import java.util.Scanner;

public class Input_Handler {
    private  Scanner scanner = new Scanner(System.in);
    
    public double getNumber() {
        double number = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter a number: ");
            try {
                number = scanner.nextDouble();
                valid = true;
            }
            catch (InputMismatchException e) {
                System.out.println(" Invalid input! Please enter a numeric value.");
                scanner.nextLine(); // Clears the invalid input
            } catch (Exception e) {
                System.out.println(" An unexpected error occurred: " + e.getMessage());
                scanner.nextLine(); // Clears the buffer
            }
        }
        return number; 
    }

    public char getOperator() {
        char operator = ' ';
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter an Operator (+, -, *, /): ");
            String input = scanner.next();

            if (input.length() == 1) {
                operator = input.charAt(0);
                
                if (operator == '+' || operator == '-' || operator == '*' || operator == '/') {
                    valid = true;
                }

                else {
                    System.out.println("Invalid operator! Please enter one of +, -, *, /.");
                }
            }
            else {
                System.out.println("Invalid input! Please enter a single character.");
                }
            }
            return operator;
        }

        public boolean askToContinue() {
            System.out.print("Do you want to perform another calculation? (y/n): ");
            String response = scanner.next().toLowerCase();
            return response.equals("y");
        }

        public void closeScanner() {
            scanner.close();
        }
    }

