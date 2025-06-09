package com.example.CarcinogenicProductIdentifier.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.example.CarcinogenicProductIdentifier.database.SessionDAO;
import com.example.CarcinogenicProductIdentifier.model.GeminiClient;
import com.example.CarcinogenicProductIdentifier.model.GeminiResponse;
import com.example.CarcinogenicProductIdentifier.model.User;
import com.example.CarcinogenicProductIdentifier.model.UserInput;
import com.example.CarcinogenicProductIdentifier.model.UserSession;
import com.google.gson.Gson;

public class MainFrame extends JFrame {
    private User loggedInUser;
    private final ChatPanel chatPanel;
    private final InputPanel inputPanel;
    private final GeminiClient geminiClient;
    private final SessionDAO sessionDAO;
    private final Gson gson;
    private JTabbedPane tabbedPane;
    private JPanel productPanel;
    private JButton moreInfoButton;

    public MainFrame(User user) {
        super("Carcinogenic Product Identifier");
        this.loggedInUser = user;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 250));

        chatPanel = new ChatPanel();
        inputPanel = new InputPanel();
        geminiClient = new GeminiClient();
        sessionDAO = new SessionDAO();
        gson = new Gson();

        // Tabs
        tabbedPane = new JTabbedPane();

        // Product Query Tab
        productPanel = new JPanel(new BorderLayout());
        productPanel.setBackground(new Color(245, 245, 250));
        productPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        productPanel.add(inputPanel, BorderLayout.CENTER);
        moreInfoButton = new JButton("Need More Info?");
        moreInfoButton.setBackground(new Color(100, 149, 237));
        moreInfoButton.setForeground(Color.WHITE);
        moreInfoButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        moreInfoButton.setFocusPainted(false);
        JPanel moreInfoPanel = new JPanel();
        moreInfoPanel.setBackground(new Color(245, 245, 250));
        moreInfoPanel.add(moreInfoButton);
        productPanel.add(moreInfoPanel, BorderLayout.SOUTH);

        // Chat Room Tab
        JPanel chatTabPanel = new JPanel(new BorderLayout());
        chatTabPanel.setBackground(new Color(245, 245, 250));
        chatTabPanel.add(chatPanel, BorderLayout.CENTER);
        tabbedPane.addTab("Product Query", productPanel);
        tabbedPane.addTab("Chat Room", chatTabPanel);
        add(tabbedPane, BorderLayout.CENTER);

        // Listeners
        inputPanel.setIdentifyListener(this::identifyButtonActionPerformed);
        chatPanel.setChatSendListener(this::chatSendButtonActionPerformed);
        moreInfoButton.addActionListener(e -> {
            // Switch to chat tab and send last query/response
            int lastIndex = tabbedPane.indexOfTab("Chat Room");
            tabbedPane.setSelectedIndex(lastIndex);
            if (lastProductQuery != null && lastProductResponse != null) {
                chatPanel.appendMessage("User: " + lastProductQuery);
                chatPanel.appendMessage("Gemini: " + lastProductResponse);
            }
        });
    }

    // Store last product query and response for 'Need More Info' button
    private String lastProductQuery = null;
    private String lastProductResponse = null;

    private void identifyButtonActionPerformed(ActionEvent e) {
        List<String> products = inputPanel.getProductInputs();
        if (products.isEmpty() || products.stream().allMatch(String::isBlank)) {
            JOptionPane.showMessageDialog(this, "Please enter at least one product.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        lastProductQuery = String.join(", ", products);
        String apiResponse = geminiClient.identifyCarcinogens(products);
        lastProductResponse = apiResponse;
        // Show the raw API response in the response area, removing asterisks, slashes, and braces
        String cleaned = apiResponse.replaceAll("[*/{}]", "");
        inputPanel.setResponseText(cleaned);
        // Optionally, also append to chat if needed
        // Save session, etc. (existing logic)

        // Save session
        UserInput userInput = new UserInput();
        userInput.setItemsUsed(products);
        GeminiResponse geminiResponse = new GeminiResponse(apiResponse);
        UserSession session = new UserSession(loggedInUser.getId(), userInput, geminiResponse);
        sessionDAO.saveSession(session);

        // Clear input area
        inputPanel.clearProductInput();
    }

    private void redirectToChatWithLastQueryAndResponse() {
        if (lastProductQuery != null && lastProductResponse != null) {
            chatPanel.appendMessage("User: " + lastProductQuery);
            chatPanel.appendMessage("Gemini: " + lastProductResponse);
            tabbedPane.setSelectedIndex(1); // Switch to Chat Room tab
        } else {
            JOptionPane.showMessageDialog(this, "No product query to send to chat.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void chatSendButtonActionPerformed(ActionEvent e) {
        String chatInput = chatPanel.getChatInput();
        if (chatInput.trim().isEmpty()) {
            return; // Do nothing if chat input is empty
        }

        // Display user message
        chatPanel.appendMessage("User: " + chatInput);

        // Call Gemini Client for chat response
        String chatResponse = geminiClient.generateChatResponse(chatInput);

        // Display Gemini's response
        chatPanel.appendMessage("Gemini: " + chatResponse);

        // Clear input area
        chatPanel.clearChatInput();

        // Save chat messages as part of the session
        // We'll treat each chat message as a new GeminiResponse and UserInput, and append to the session
        UserInput userInput = new UserInput();
        userInput.setItemsUsed(Arrays.asList(chatInput));
        GeminiResponse geminiResponse = new GeminiResponse(chatResponse);
        UserSession session = new UserSession(loggedInUser.getId(), userInput, geminiResponse);
        sessionDAO.saveSession(session);
    }
}