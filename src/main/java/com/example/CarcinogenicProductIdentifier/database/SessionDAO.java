package com.example.CarcinogenicProductIdentifier.database;

import com.example.CarcinogenicProductIdentifier.model.GeminiResponse;
import com.example.CarcinogenicProductIdentifier.model.UserInput;
import com.example.CarcinogenicProductIdentifier.model.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO {

    public void saveSession(UserSession session) {
        String sql = "INSERT INTO sessions(user_id, user_input, gemini_response) VALUES(?,?,?)";
        try (Connection conn = DatabaseAccessor.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, session.getUserId());
            pstmt.setString(2, String.join(",", session.getUserInput().getItemsUsed()));
            pstmt.setString(3, session.getGeminiResponse().getResponseText());
            pstmt.executeUpdate();
            System.out.println("Session saved successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<UserSession> getSessionsByUserId(int userId) {
        String sql = "SELECT user_input, gemini_response FROM sessions WHERE user_id = ?";
        List<UserSession> sessions = new ArrayList<>();
        try (Connection conn = DatabaseAccessor.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserSession session = new UserSession();
                session.setUserId(userId);
                String userInputStr = rs.getString("user_input");
                if (userInputStr != null) {
                    List<String> itemsUsed = new ArrayList<>();
                    for (String item : userInputStr.split(",")) {
                        itemsUsed.add(item.trim());
                    }
                    UserInput userInput = new UserInput();
                    userInput.setItemsUsed(itemsUsed);
                    session.setUserInput(userInput);
                }
                String geminiResponseText = rs.getString("gemini_response");
                if (geminiResponseText != null) {
                    GeminiResponse geminiResponse = new GeminiResponse();
                    geminiResponse.setResponseText(geminiResponseText);
                    session.setGeminiResponse(geminiResponse);
                }
                sessions.add(session);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sessions;
    }
}