package frameworkCore;

public class StaticContext extends ContextElement{
	private String sourcePath;
	
	
	
	public StaticContext(String key, Object value, String sourcePath) {
		super(key, value);
		this.sourcePath = sourcePath;
	}



	public String getSourcePath() {
		return sourcePath;
	}
}
