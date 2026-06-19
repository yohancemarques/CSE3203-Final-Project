package frameworkCore;

public class DomainContext extends ContextElement{
	private String domainSchemaID;
	
	
	
	public DomainContext(String key, Object value, String domainSchemaID) {
		super(key, value);
		this.domainSchemaID = domainSchemaID;
	}



	public String getDomainSchema() {
		return domainSchemaID;
	}
}

