
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Employee Payroll System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 320);

            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            panel.setBackground(new Color(255, 255, 255));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Input File Label
            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(new JLabel("Input File:"), gbc);

            // Input File Path Field
            JTextField inputFileField = new JTextField(20);
            inputFileField.setEditable(false);
            gbc.gridx = 1;
            gbc.gridy = 0;
            panel.add(inputFileField, gbc);

            // Browse Button
            JButton browseButton = new JButton("Browse");
            browseButton.setBackground(new Color(60, 179, 113));
            browseButton.setForeground(Color.WHITE);
            browseButton.setFocusPainted(false);
            gbc.gridx = 2;
            gbc.gridy = 0;
            panel.add(browseButton, gbc);

            // Output File Path Field
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(new JLabel("Output File:"), gbc);

            JTextField outputFileField = new JTextField("", 20);
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridwidth = 2;
            panel.add(outputFileField, gbc);

            // Calculate Button
            JButton calculateButton = new JButton("Calculate Payroll");
            calculateButton.setBackground(new Color(100, 149, 237));
            calculateButton.setForeground(Color.WHITE);
            calculateButton.setFocusPainted(false);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 3;
            panel.add(calculateButton, gbc);

            // Message Label for displaying success or error messages
            JLabel messageLabel = new JLabel("");
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 3;
            panel.add(messageLabel, gbc);

            // Browse Button Action
            browseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    int option = fileChooser.showOpenDialog(frame);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        inputFileField.setText(selectedFile.getAbsolutePath());
                    }
                }
            });

            // Calculate Button Action
            calculateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String inputFile = inputFileField.getText();
                    String outputFile = outputFileField.getText();

                    if (inputFile.isEmpty()) {
                        messageLabel.setText("Please select an input file.");
                        messageLabel.setForeground(Color.RED);
                        return;
                    }

                    PayrollSystem payrollSystem = new PayrollSystem();
                    try {
                        List<Employee> employees = payrollSystem.loadEmployeesFromFile(inputFile);
                        // Only proceed to calculate and save payroll if employee data is valid
                        payrollSystem.calculateAndSavePayroll(outputFile, employees);
                        messageLabel.setText("Payroll data written to " + outputFile);
                        messageLabel.setForeground(new Color(34, 139, 34)); // Green color for success
                    } catch (IOException ex) {
                        messageLabel.setText("Error: " + ex.getMessage());
                        messageLabel.setForeground(Color.RED); // Red color for errors
                    }
                }
            });
            frame.add(panel);
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
        });
    }
}
