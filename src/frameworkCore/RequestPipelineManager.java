package frameworkCore;
import java.util.List;
import java.util.ArrayList;

public class RequestPipelineManager {
	private List<RequestInterceptor> interceptors;
	private List<ContextProvider> providers;
	private PromptBuilder promptBuilder;
	
	public RequestPipelineManager(List<RequestInterceptor> interceptors, List<ContextProvider> providers, PromptBuilder promptBuilder) {
		super();
		this.interceptors = interceptors;
		this.providers = providers;
		this.promptBuilder = promptBuilder;
	}
	
	public String executePipeline(UserRequest request) {
		RequestContext context = new RequestContext(new ArrayList<>());
		
		for(ContextProvider provider : providers) {
			List<ContextElement> elements = provider.getContext(request);
			if (elements != null) {
				for (ContextElement element : elements) {
					context.addElement(element);
				}
			}
		}
		
		for (RequestInterceptor interceptor : interceptors) {
			boolean allowed = interceptor.intercept(request, context);
			if(!allowed) {
				return null; //block request
			}
		}
		
		// Build and return the final prompt
		return promptBuilder.buildPrompt(request, context);
	}
	
}	
 