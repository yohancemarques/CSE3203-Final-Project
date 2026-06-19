package frameworkCore;
import java.util.List;

public interface ContextProvider {
	public List<ContextElement> getContext(UserRequest request);
}
