package frameworkCore;

public class FormattingInterceptor implements ResponseInterceptor {

	@Override
	public ProviderResponse intercept(ProviderResponse response) {
		// Intended: reformat response.getContent() into required structure (e.g. JSON, plain text)
        System.out.println("[FORMATTER] Response formatted.");
        return response; // pass through
	}

}
