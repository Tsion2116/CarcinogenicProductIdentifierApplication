package com.example.CarcinogenicProductIdentifier.model;

public class GeminiResponse {
    private String responseText;

    public GeminiResponse() {
    }

    public GeminiResponse(String responseText) {
        this.responseText = responseText;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
