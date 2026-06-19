package frameworkCore;

public class PromptBuilder {
	private String systemInstructions;
	
	public String buildPrompt(UserRequest request, RequestContext context) {
		StringBuilder prompt = new StringBuilder();

        // Pull system instructions from context if present
        ContextElement sysInstr = context.getElementbyKey("systemInstructions");
        if (sysInstr != null) {
            prompt.append("SYSTEM: ").append(sysInstr.getValue()).append("\n\n");
        }

        // Append relevant context elements
        for (ContextElement element : context.getAllElements()) {
            if (!element.getKey().equals("systemInstructions")) {
                prompt.append("[").append(element.getKey()).append("]: ")
                      .append(element.getValue()).append("\n");
            }
        }

        prompt.append("\nUSER: ").append(request.getUserInput());
        return prompt.toString();
	}
	
}

