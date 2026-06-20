package frameworkCore;
import java.util.List;

public class LLMFramework {
	private RequestPipelineManager requestPipeline;
	private ResponsePipelineManager responsePipeline;
	private LLMAdapter llmAdapter;
	private List<FrameworkAspect> aspects;


	public LLMFramework(RequestPipelineManager requestPipeline, ResponsePipelineManager responsePipeline,
			LLMAdapter llmAdapter) {
		this(requestPipeline, responsePipeline, llmAdapter, List.of(new PerformanceMonitoringAspect()));
	}
	
	
	public LLMFramework(RequestPipelineManager requestPipeline, ResponsePipelineManager responsePipeline, LLMAdapter llmAdapter, List<FrameworkAspect> aspects) {
		super();
		this.requestPipeline = requestPipeline;
		this.responsePipeline = responsePipeline;
		this.llmAdapter = llmAdapter;
		this.aspects = aspects;
	}



	public FrameworkResponse processRequest(UserRequest request) {
	    // 1. Run request pipeline — returns final prompt string
	    String finalPrompt = runStage(requestPipeline, () -> requestPipeline.executePipeline(request));
	    if (finalPrompt == null) {
	        FrameworkResponse blocked = new FrameworkResponse();
	        blocked.setOutputText("Request was blocked by the security interceptor.");
	        blocked.setValidated(false);
	        return blocked;
	    }

	    // 2. Send prompt to LLM adapter
	    ProviderResponse providerResponse = runStage(llmAdapter, () -> llmAdapter.generateResponse(finalPrompt));

	    // 3. Run response pipeline and return
	    return runStage(responsePipeline, () -> responsePipeline.executePipeline(providerResponse));
	}
	
	// Support for aspects
	private <T> T runStage(Object target, java.util.function.Supplier<T> stage) {
		for (FrameworkAspect aspect : aspects) {
			aspect.before(target, new Object[]{});
		}
		try {
			T result = stage.get();
			for (FrameworkAspect aspect : aspects) {
				aspect.after(target, result);
			}
			return result;
		} catch (RuntimeException e) {
			for (FrameworkAspect aspect : aspects) {
				aspect.onError(target, e);
			}
			throw e;
		}
	}
}
