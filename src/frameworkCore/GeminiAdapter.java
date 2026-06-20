// I used this as a guide https://ai.google.dev/gemini-api/docs/api-key#java
// The Gemini dependency is in the pom.xml file at the root of this project.
// A free version of Gemini AI is used for demonstration purposes since Anthropic and OpenAI do not offer free API usage.
// Generate a Gemini API key here https://aistudio.google.com/api-keys
// The API key is pulled from the environment

package frameworkCore;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import java.util.Map;

public class GeminiAdapter implements LLMAdapter {

    private final Client client;
    private static final String MODEL = "gemini-2.5-flash";

    public GeminiAdapter() {
    	String apiKey = System.getenv("GEMINI_API_KEY");
        this.client = Client.builder()
            .apiKey(apiKey) 
            .build();
    }

    @Override
    public ProviderResponse generateResponse(String finalPrompt) {
        try {
            GenerateContentResponse geminiResponse = client.models.generateContent(
                MODEL,
                finalPrompt,
                null
            );

            ProviderResponse response = new ProviderResponse();
            response.setContent(geminiResponse.text());
            response.setTokenUsage(0); 
            response.setMetadata(Map.of("model", MODEL));
            return response;

        } catch (Exception e) {
            ProviderResponse error = new ProviderResponse();
            error.setContent("Error contacting Gemini API: " + e.getMessage());
            error.setTokenUsage(0);
            return error;
        }
    }
}

