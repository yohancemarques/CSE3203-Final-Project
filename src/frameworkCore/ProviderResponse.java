package frameworkCore;
import java.util.Map;

public class ProviderResponse {
	private String content;
	private int tokenUsage;
	private Map<String, Object> metadata;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTokenUsage() {
		return tokenUsage;
	}
	public void setTokenUsage(int tokenUsage) {
		this.tokenUsage = tokenUsage;
	}
	public Map<String, Object> getMetadata() {
		return metadata;
	}
	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}
}
 