package frameworkCore;
import java.util.List;

public class PromptBuilder {
	private String systemInstructions;
	
	public String buildPrompt(UserRequest request, RequestContext context) {
		StringBuilder prompt = new StringBuilder();

		// 1. Add system instructions to prompt (taken from context if present, otherwise fallback to field)
		ContextElement sysElement = context.getElementbyKey("systemInstructions");
		String instructions = (sysElement != null)
			? sysElement.getValue().toString()
			: (systemInstructions != null ? systemInstructions : "You are a helpful assistant.");
		prompt.append("SYSTEM: ").append(instructions).append("\n\n");

		// 2. Add domain rules to prompt
		ContextElement domainElement = context.getElementbyKey("domainRules");
		if (domainElement != null) {
			prompt.append("DOMAIN RULES: ").append(domainElement.getValue()).append("\n\n");
		}

		// 3. Add conversation history to prompt
		ContextElement historyElement = context.getElementbyKey("conversationHistory");
		if (historyElement != null) {
			Object history = historyElement.getValue();
			if (history instanceof List<?> turns) {
				prompt.append("CONVERSATION HISTORY:\n");
				for (Object turn : turns) {
					prompt.append("  - ").append(turn).append("\n");
				}
				prompt.append("\n");
			}
		}

		// 4. Add user profile metadata (role, language, etc.) to prompt
		ContextElement profileElement = context.getElementbyKey("userProfile");
		if (profileElement != null) {
			prompt.append("USER PROFILE: ").append(profileElement.getValue()).append("\n\n");
		}

		// 5. Add the user's actual request to the prompt
		prompt.append("USER: ").append(request.getUserInput());
		
		// 6. Return all of the appended data as a string
		return prompt.toString();
	}
}