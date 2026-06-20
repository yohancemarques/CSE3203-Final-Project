package frameworkCore;

public class StructureValidator implements ResponseValidator {
	private static final int MAX_RESPONSE_LENGTH = 10_000;
	private static final int MIN_RESPONSE_LENGTH = 5;

	@Override
	public boolean validate(ProviderResponse response) {
		if (response == null || response.getContent() == null) {
			System.out.println("[VALIDATOR] Failed: response or content is null.");
			return false;
		}

		String content = response.getContent().trim();
		
		// Check the length of the response
		if (content.isEmpty() || content.length() < MIN_RESPONSE_LENGTH) {
			System.out.println("[VALIDATOR] Failed: response too short (" + content.length() + " chars).");
			System.out.println("The minimum response length is " + MIN_RESPONSE_LENGTH + ".");
			return false;
		}

		if (content.length() > MAX_RESPONSE_LENGTH) {
			System.out.println("[VALIDATOR] Failed: response too long (" + content.length() + " chars).");
			System.out.println("The maximum response length is " + MAX_RESPONSE_LENGTH + ".");
			return false;
		}

		// Check for error signals from the LLM adapter 
		if (content.startsWith("Error contacting")) {
			System.out.println("[VALIDATOR] Failed: upstream API error in response.");
			return false;
		}

		System.out.println("[VALIDATOR] Response passed all checks.");
		return true;
	}
}