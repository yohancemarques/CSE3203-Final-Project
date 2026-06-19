// I used this as a guide:
// https://platform.claude.com/docs/en/cli-sdks-libraries/sdks/java#maven

package frameworkCore;
import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.*;

public class ClaudeAdapter implements LLMAdapter {
	
	private AnthropicClient client;
	
		
	
	public ClaudeAdapter() {
		super();
		this.client = AnthropicOkHttpClient.fromEnv();
	}



	@Override
	public ProviderResponse generateResponse(String finalPrompt) {
		MessageCreateParams params = MessageCreateParams.builder()
        .model(Model.CLAUDE_SONNET_4_6)
        .maxTokens(1024)
        .addUserMessage(finalPrompt)
        .build();
		
		Message message = client.messages().create(params);
		
		String responseText = message.content().stream()
			    .filter(block -> block.isText())
			    .map(block -> block.asText().text())
			    .findFirst()
			    .orElse("");

	        ProviderResponse response = new ProviderResponse();
	        response.setContent(responseText);
	        response.setTokenUsage((int) message.usage().outputTokens());
	        return response;
		
	}

}

