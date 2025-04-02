import java.awt.*;
import javax.swing.*;

public class CalculatorGUI extends JFrame {
    private final Input_Handler inputHandler = new Input_Handler();
    private final Operations operations = new Operations();
    private final HistoryManager historyManager = new HistoryManager();

    private JTextField displayField;
    private JTextArea historyArea;
    private String currentInput = "";
    private double num1 = 0;
    private char operator;

    public CalculatorGUI() {
        setTitle("Simple Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 244, 248));

        createDisplay();
        createButtons();

        setLocationRelativeTo(null); // center screen
        setVisible(true);
    }

    private void createDisplay() {
        displayField = new JTextField();
        displayField.setFont(new Font("Arial", Font.BOLD, 24));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false);
        displayField.setBackground(Color.WHITE);
        displayField.setForeground(new Color(51, 51, 51));
        displayField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(displayField, BorderLayout.NORTH);
    }

    private void createButtons() {
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(new Color(240, 244, 248));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*", 
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "History"
        };

        for (String label : buttons) {
            JButton button = new JButton(label);
            styleButton(button);

            button.addActionListener(e -> handleButton(label));
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(74, 144, 226));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(74, 144, 226), 1));
    }

    private void handleButton(String label) {
        switch (label) {
            case "=" -> calculateResult();
            case "C" -> {
                currentInput = "";
                displayField.setText("");
            }
            case "History" -> showHistoryPopup();
            case "+", "-", "*", "/" -> {
                try {
                    num1 = Double.parseDouble(currentInput);
                    operator = label.charAt(0);
                    currentInput = "";
                    displayField.setText("");
                } catch (Exception e) {
                    showError("Invalid number input.");
                }
            }
            default -> {
                currentInput += label;
                displayField.setText(currentInput);
            }
        }
    }

    private void calculateResult() {
        try {
            double num2 = Double.parseDouble(currentInput);
            double result = switch (operator) {
                case '+' -> Operations.add(num1, num2);
                case '-' -> Operations.subtract(num1, num2);
                case '*' -> Operations.multiply(num1, num2);
                case '/' -> Operations.divide(num1, num2);
                default -> throw new IllegalStateException("Invalid operator");
            };
            displayField.setText(String.valueOf(result));
            historyManager.addRecord(num1, num2, operator, result);
            currentInput = String.valueOf(result); // Allow chaining
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void showHistoryPopup() {
        JFrame historyFrame = new JFrame("Calculation History");
        historyFrame.setSize(300, 400);
        historyFrame.setLayout(new BorderLayout());

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        if (historyManager.getHistory().isEmpty()) {
            historyArea.setText("No history yet.");
        } else {
            historyArea.setText("Calculation History:\n\n");
            for (String entry : historyManager.getHistory()) {
                historyArea.append(entry + "\n");
            }
        }

        JScrollPane scrollPane = new JScrollPane(historyArea);
        historyFrame.add(scrollPane, BorderLayout.CENTER);
        historyFrame.setLocationRelativeTo(this);
        historyFrame.setVisible(true);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorGUI::new);
    }
}
