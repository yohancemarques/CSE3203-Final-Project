// I used this as a guide
// https://ai.google.dev/gemini-api/docs/api-key#java
// A free version of Gemini AI is added for demonstration purposes since Anthropic and OpenAI don't offer free API usage

package frameworkCore;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import java.util.Map;

public class GeminiAdapter implements LLMAdapter {

    private final Client client;
    private static final String MODEL = "gemini-2.5-flash";

    public GeminiAdapter() {
        this.client = Client.builder()
            .apiKey("") // please don't abuse my api key
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

