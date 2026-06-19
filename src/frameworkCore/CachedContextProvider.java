package frameworkCore;
import java.util.List;
import java.util.Map;

public class CachedContextProvider implements ContextProvider{
	private Map<String, List<ContextElement>> cacheStore;
	private ContextProvider provider;
	
	@Override
	public List<ContextElement> getContext(UserRequest request) {
		return null;
	}
}
