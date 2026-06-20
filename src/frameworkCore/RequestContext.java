package frameworkCore;
import java.util.List;

public class RequestContext {
	private List<ContextElement> elements;
	
	
	
	public RequestContext(List<ContextElement> elements) {
		super();
		this.elements = elements;
	}

	public void addElement(ContextElement element) {
		elements.add(element);
	}
	

	public ContextElement getElementbyKey(String key) {
		for (ContextElement element : elements) {
			if (element.getKey().equals(key)) {
				return element;
			}
		}
	    return null;
	}
	
	public List<ContextElement> getAllElements() {
		return elements;
	}
}
