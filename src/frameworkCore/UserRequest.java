package frameworkCore;
import java.util.Map;


public class UserRequest {
	private String userInput;
	private String userId;
	private Map<String, Object> metadata;
	
	public UserRequest(String userInput, String userId, Map<String,Object> metadata) {
		super();
		this.userInput = userInput;
		this.userId = userId;
		this.metadata = metadata;
	}
	
	public String getUserInput() {
		return userInput;
	}
	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Map<String, Object> getMetadata() {
		return metadata;
	}
	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}
	
}
