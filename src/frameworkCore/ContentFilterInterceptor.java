package frameworkCore;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentFilterInterceptor implements ResponseInterceptor {
	private List<Pattern> sensitivePatterns;

	public ContentFilterInterceptor() {
		// Default sensitive patterns: SSNs, credit card numbers, email addresses
		// SSN regex: https://www.geeksforgeeks.org/dsa/how-to-validate-ssn-social-security-number-using-regular-expression/
		// Email regex: https://balasubramanyamlanka.com/how-to-validate-an-email-using-regex-in-java/
		// Credit card regex: https://gist.github.com/arundvp/188d92fefda9bb7546ee52a9ecf7aad6
		this.sensitivePatterns = List.of(
			Pattern.compile("^(?!666|000|9\\\\d{2})\\\\d{3}-(?!00)\\\\d{2}-(?!0{4})\\\\d{4}$"),          // SSN
			Pattern.compile("^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|6(?:011|5[0-9]{2})[0-9]{12}|(?:2131|1800|35\\d{3})\\d{11})$"), // credit card
			Pattern.compile("^[_A-Za-z0-9-]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9-]+)*(.[A-Za-z]{2,})$") // email
		);
	}

	public ContentFilterInterceptor(List<Pattern> sensitivePatterns) {
		this.sensitivePatterns = sensitivePatterns;
	}

	@Override
	public ProviderResponse intercept(ProviderResponse response) {
		if (response == null || response.getContent() == null) return response;

		String content = response.getContent();
		for (Pattern pattern : sensitivePatterns) {
			Matcher matcher = pattern.matcher(content);
			content = matcher.replaceAll("[REDACTED]");
		}

		if (!content.equals(response.getContent())) {
			System.out.println("[CONTENT FILTER] Sensitive data redacted from response.");
			response.setContent(content);
		} else {
			System.out.println("[CONTENT FILTER] No sensitive content found.");
		}

		return response;
	}
}