package frameworkCore;
import java.util.List;
import java.net.http.*;

public class APIContextProvider implements ContextProvider{
	private HttpClient httpClient;
	private String endpointURL;
	
	
	@Override
	public List<ContextElement> getContext(UserRequest request) {
		return null;
	}
}
