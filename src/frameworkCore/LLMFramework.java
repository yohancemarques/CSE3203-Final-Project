package frameworkCore;

public class LLMFramework {
	private RequestPipelineManager requestPipeline;
	private ResponsePipelineManager responsePipeline;
	private LLMAdapter llmAdapter;
	
	
	
	public LLMFramework(RequestPipelineManager requestPipeline, ResponsePipelineManager responsePipeline,
			LLMAdapter llmAdapter) {
		super();
		this.requestPipeline = requestPipeline;
		this.responsePipeline = responsePipeline;
		this.llmAdapter = llmAdapter;
	}



	public FrameworkResponse processRequest(UserRequest request) {
	    // 1. Run request pipeline — returns final prompt string
	    String finalPrompt = requestPipeline.executePipeline(request);
	    if (finalPrompt == null) {
	        FrameworkResponse blocked = new FrameworkResponse();
	        blocked.setOutputText("Request was blocked by the security interceptor.");
	        blocked.setValidated(false);
	        return blocked;
	    }

	    // 2. Send prompt to LLM adapter
	    ProviderResponse providerResponse = llmAdapter.generateResponse(finalPrompt);

	    // 3. Run response pipeline and return
	    return responsePipeline.executePipeline(providerResponse);
	}
}
