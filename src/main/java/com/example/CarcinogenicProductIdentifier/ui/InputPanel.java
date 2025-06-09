package com.example.CarcinogenicProductIdentifier.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class InputPanel extends JPanel implements ActionListener {
    private JTextField inputField;
    private JButton sendButton;
    private List<JTextField> productFields;
    private JButton addProductButton;
    private JPanel productFieldsPanel;
    private JButton identifyButton;
    private JTextArea responseArea;

    // Listener for sending chat messages
    private ActionListener chatSendListener;
    // Listener for sending product list for identification
    private ActionListener identifyListener;

    public InputPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        // Product input fields panel (vertical)
        productFieldsPanel = new JPanel();
        productFieldsPanel.setLayout(new BoxLayout(productFieldsPanel, BoxLayout.Y_AXIS));
        productFieldsPanel.setBackground(new Color(245, 245, 250));
        productFieldsPanel.setBorder(BorderFactory.createTitledBorder("Enter products you use (one per field)"));
        productFields = new ArrayList<>();
        addProductField(); // Add initial field

        // Add button to add more product fields
        addProductButton = new JButton("+");
        addProductButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        addProductButton.setBackground(new Color(60, 179, 113));
        addProductButton.setForeground(Color.WHITE);
        addProductButton.setFocusPainted(false);
        addProductButton.addActionListener(e -> addProductField());
        JPanel addBtnPanel = new JPanel();
        addBtnPanel.setBackground(new Color(245, 245, 250));
        addBtnPanel.add(addProductButton);

        JPanel productInputPanel = new JPanel(new BorderLayout());
        productInputPanel.setBackground(new Color(245, 245, 250));
        productInputPanel.add(productFieldsPanel, BorderLayout.CENTER);
        productInputPanel.add(addBtnPanel, BorderLayout.SOUTH);

        // Identify button
        identifyButton = new JButton("Identify Carcinogens");
        identifyButton.setBackground(new Color(60, 179, 113));
        identifyButton.setForeground(Color.WHITE);
        identifyButton.setFocusPainted(false);
        identifyButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        identifyButton.addActionListener(this);
        JPanel identifyBtnPanel = new JPanel();
        identifyBtnPanel.setBackground(new Color(245, 245, 250));
        identifyBtnPanel.add(identifyButton);
        productInputPanel.add(identifyBtnPanel, BorderLayout.EAST);

        // Add product input panel to the main InputPanel
        add(productInputPanel, BorderLayout.NORTH);

        // Add response area below product input
        responseArea = new JTextArea(5, 30);
        responseArea.setEditable(false);
        responseArea.setLineWrap(true);
        responseArea.setWrapStyleWord(true);
        responseArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        responseArea.setBackground(new Color(250, 250, 255));
        responseArea.setForeground(new Color(40, 40, 60));
        responseArea.setBorder(BorderFactory.createTitledBorder("Gemini API Response"));
        JScrollPane responseScrollPane = new JScrollPane(responseArea);
        responseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        responseScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(responseScrollPane, BorderLayout.CENTER);
    }

    private void addProductField() {
        JTextField productField = new JTextField();
        productField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        productField.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 30));
        productFields.add(productField);
        productFieldsPanel.add(productField);
        productFieldsPanel.revalidate();
        productFieldsPanel.repaint();
    }

    public void setChatSendListener(ActionListener listener) {
        this.chatSendListener = listener;
    }

    public void setIdentifyListener(ActionListener listener) {
        this.identifyListener = listener;
    }

    // Add this method to InputPanel to get all product inputs from the dynamic text fields
    public List<String> getProductInputs() {
        List<String> products = new ArrayList<>();
        for (JTextField field : productFields) {
            String text = field.getText().trim();
            if (!text.isEmpty()) {
                products.add(text);
            }
        }
        return products;
    }

    public String getProductInput() {
        // Deprecated: use getProductInputs()
        return String.join("\n", getProductInputs());
    }

    public void clearProductInput() {
        for (JTextField field : productFields) {
            field.setText("");
        }
    }

    public void setApiResponse(String response) {
        // Remove asterisks, slashes, and braces for cleaner display
        String cleaned = response.replaceAll("[*/{}]", "");
        responseArea.setText(cleaned.trim());
    }

    public void setResponseText(String text) {
        responseArea.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            if (chatSendListener != null) {
                chatSendListener.actionPerformed(e);
            }
        } else if (e.getSource() == identifyButton) {
            if (identifyListener != null) {
                identifyListener.actionPerformed(e);
            }
        }
    }
}
