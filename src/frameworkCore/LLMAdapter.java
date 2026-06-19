package frameworkCore;

public interface LLMAdapter {
	public ProviderResponse generateResponse(String finalPrompt);
}
