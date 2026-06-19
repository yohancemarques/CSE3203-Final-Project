package frameworkCore;

import java.util.Map;

public class LocalModelAdapter implements LLMAdapter {
	
	private String localEndpoint;
	
	@Override
	public ProviderResponse generateResponse(String finalPrompt) {
	    ProviderResponse response = new ProviderResponse();
	    response.setContent("Looking at your profile, you have yet to receive your grade in the courses CSE3200, CSE3201, CSE3203, and FRE1000.");
	    response.setTokenUsage(42);
	    response.setMetadata(Map.of("model", "local-stub", "latency_ms", 120));
	    return response;
	}

}
 