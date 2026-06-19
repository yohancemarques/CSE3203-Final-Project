package frameworkCore;

public abstract class ContextElement {
	protected String key;
	protected Object value;
	
	
	
	public ContextElement(String key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}
	
	public Object getValue() {
		return value;
	}
}
