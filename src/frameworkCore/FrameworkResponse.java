package frameworkCore;
import java.util.Map;


public class FrameworkResponse {
	private String outputText;
	private boolean isValidated;
	private Map<String, Object> executionMetrics;
	
	public String getOutputText() {
		return outputText;
	}
	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}
	public boolean isValidated() {
		return isValidated;
	}
	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}
	public Map<String, Object> getExecutionMetrics() {
		return executionMetrics;
	}
	public void setExecutionMetrics(Map<String, Object> executionMetrics) {
		this.executionMetrics = executionMetrics;
	}
	
	
}
 