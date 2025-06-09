package com.example.CarcinogenicProductIdentifier.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPanel extends JPanel {
    private JTextArea chatArea;
    private JScrollPane scrollPane;
    private JTextField chatInputField;
    private JButton sendButton;
    private ActionListener chatSendListener;

    public ChatPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createLineBorder(new Color(180, 180, 220), 1, true)
        ));

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chatArea.setBackground(new Color(250, 250, 255));
        chatArea.setForeground(new Color(40, 40, 60));

        scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Chat input field and send button at the bottom
        JPanel inputPanel = new JPanel(new BorderLayout());
        chatInputField = new JTextField();
        chatInputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(100, 149, 237));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        sendButton.addActionListener(e -> {
            if (chatSendListener != null) {
                chatSendListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "send"));
            }
        });
        inputPanel.add(chatInputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }

    public String getChatInput() {
        return chatInputField.getText();
    }

    public void clearChatInput() {
        chatInputField.setText("");
    }

    public void setChatSendListener(ActionListener listener) {
        this.chatSendListener = listener;
    }
}
