package com.example.CarcinogenicProductIdentifier.model;

public class UserSession {
    private int userId;
    private UserInput userInput;
    private GeminiResponse geminiResponse;

    public UserSession() {
    }

    public UserSession(int userId, UserInput userInput, GeminiResponse geminiResponse) {
        this.userId = userId;
        this.userInput = userInput;
        this.geminiResponse = geminiResponse;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserInput getUserInput() {
        return userInput;
    }

    public void setUserInput(UserInput userInput) {
        this.userInput = userInput;
    }

    public GeminiResponse getGeminiResponse() {
        return geminiResponse;
    }

    public void setGeminiResponse(GeminiResponse geminiResponse) {
        this.geminiResponse = geminiResponse;
    }
}
