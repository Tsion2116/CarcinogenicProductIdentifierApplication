package com.example.CarcinogenicProductIdentifier.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.example.CarcinogenicProductIdentifier.database.UserDAO;
import com.example.CarcinogenicProductIdentifier.model.User;

public class LoginFrame extends JFrame  {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton registerButton;
    private final UserDAO userDAO;

    public LoginFrame() {
        super("Login or Register");

        userDAO = new UserDAO();

        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        getContentPane().setBackground(new Color(245, 245, 250));

        // Use a panel for form fields with padding and rounded border
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 20, 10, 20),
            BorderFactory.createLineBorder(new Color(180, 180, 220), 2, true)
        ));
        formPanel.setBackground(new Color(255, 255, 255));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        add(formPanel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 250));
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(100, 149, 237));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(60, 179, 113));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        add(buttonPanel, java.awt.BorderLayout.SOUTH);

        // Add action listeners inside the constructor
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User authenticatedUser = userDAO.getUserByUsername(username);
            if (authenticatedUser != null && userDAO.authenticateUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                MainFrame mainFrame = new MainFrame(authenticatedUser);
                mainFrame.setVisible(true); // Use setVisible instead of display()
                dispose(); // Close login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            // Open the registration form as a dialog
            RegistrationForm registrationForm = new RegistrationForm();
            registrationForm.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}