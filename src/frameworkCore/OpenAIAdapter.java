// This adapter has not been implemented 
// The OpenAI dependency is in the pom.xml file at the root of this project.

package frameworkCore;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;

public class OpenAIAdapter implements LLMAdapter {
	private OpenAIClient client;
	
	
	@Override
	public ProviderResponse generateResponse(String finalPrompt) {
		
		return null;
	}
}
