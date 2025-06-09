package com.example.CarcinogenicProductIdentifier.model;

import java.util.List;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;

/**
 * GeminiClient is a client for interacting with the Gemini AI model.
 * It handles generating content based on user queries and chat history.
 */
public class GeminiClient {
    /**
     * The name of the Gemini model used for generating content.
     */
    private final String MODEL_NAME = "gemini-2.0-flash";
    
    /**
     * The system prompt used for generating content.
     */
    private final String SYSTEM_PROMPT1 = "You are a carcinogenic product identifier assistant. " +
        "Given a list of items a user uses: " +
        "1. Which items are carcinogenic, which are not. " +
        "2. Carcinogenic percentage for each item. " +
        "3. Prevention measures for each carcinogenic item. " +
        "4. Which items the user must not use. " +
        "5. Lookup in this https://world.openfoodfacts.org/ website about the product";
        
    private final String SYSTEM_PROMPT2 = "You are a helpful assistant. " +
        "Answer the user's questions based on the chat history. " +
        "Respond in a concise manner unless the user requests more details. " +
        "If you don't know the answer, say 'I don't know'." +
        "Make sure to provide accurate and helpful information." +
        "You can also provide additional context or explanations if necessary.";
    /**
     * The API key used for authenticating with the Gemini API.
     */
    private static final String API_KEY = "AIzaSyCz4P3Ywm2e_7AG8Z67Phr7GVMUaW7D8-w"; 

    /**
     * The chat history of the conversation between the user and the assistant.
     */
    private StringBuilder chatHistory = new StringBuilder();
    
    /**
     * The Gemini client used for generating content.
     */
    private final Client client;
    
    /**
     * Constructs a new GeminiClient instance.
     */
    public GeminiClient() {
        client = Client.builder().apiKey(API_KEY).build();
    }
    
    /**
     * Adds a message to the chat history.
     * 
     * @param role the role of the message (e.g. "user" or "model")
     * @param content the content of the message
     */
    private void addToChatHistory(String role, String content) {
        chatHistory.append(role).append(": ").append(content).append("\n");
    }

    /**
     * Generates a JSON response for carcinogenic product identification.
     *
     * @param products the list of products the user uses
     * @return the generated JSON content
     */
    public String identifyCarcinogens(List<String> products) {
        String prompt = "Identify carcinogenic products in the following list: " + String.join(", ", products);
        try {
            GenerateContentConfig config = GenerateContentConfig.builder().systemInstruction(Content.fromParts(
                Part.fromText(SYSTEM_PROMPT1),
                Part.fromText("make answers brief unless required by the user")
            )).build();
            addToChatHistory("user", prompt);
            GenerateContentResponse response = client.models.generateContent(MODEL_NAME, chatHistory.toString(), config);
            String reply = response.text();
            addToChatHistory("model", reply);
            return reply;
        } catch (Exception e) {
            throw new RuntimeException("Error generating content: " + e.getMessage(), e);
        }
    }
    
    /**
     * Generates a chat response based on the user's prompt and chat history.
     *
     * @param prompt the user's chat message
     * @return the generated chat response
     */
    public String generateChatResponse(String prompt) {
        try {
            addToChatHistory("user", prompt);
            GenerateContentConfig config = GenerateContentConfig.builder().systemInstruction(Content.fromParts(
                Part.fromText(SYSTEM_PROMPT2),
                Part.fromText("discuss the user's query based on the chat history.")
            )).build();
            GenerateContentResponse response = client.models.generateContent(MODEL_NAME, chatHistory.toString(), config);
            String reply = response.text();
            addToChatHistory("model", reply);
            return reply;
        } catch (Exception e) {
            throw new RuntimeException("Error generating chat response: " + e.getMessage(), e);
        }
    }

    /**
     * Clears the chat history.
     */
    public void clearChatHistory() {
        chatHistory = new StringBuilder();
    }
}
