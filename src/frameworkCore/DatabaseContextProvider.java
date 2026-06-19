package frameworkCore;
import java.sql.Connection;
import java.util.List;

public class DatabaseContextProvider implements ContextProvider {
	Connection dbConnection;
	
	@Override
	public List<ContextElement> getContext(UserRequest request) {
		return null;
	}
}
