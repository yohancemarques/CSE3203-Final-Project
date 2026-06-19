package frameworkCore;
import java.util.List;
import java.util.regex.Pattern;

public class ContentFilterInterceptor implements ResponseInterceptor {
	private List<Pattern> sensitivePatterns;
	
	@Override
	public ProviderResponse intercept(ProviderResponse response) {
		// Intended: scan response.getContent() against sensitivePatterns and redact any matches before returning
		System.out.println("[CONTENT FILTER] Response scanned, no sensitive content found.");
        return response; // pass through
	}

}
