package frameworkCore;
import java.util.List;
import java.util.Map;

public class ResponsePipelineManager {
	private List<ResponseInterceptor> interceptors;
	private List<ResponseValidator> validators;
	
	
	
	public ResponsePipelineManager(List<ResponseInterceptor> interceptors, List<ResponseValidator> validators) {
		super();
		this.interceptors = interceptors;
		this.validators = validators;
	}



	public FrameworkResponse executePipeline(ProviderResponse response) {
	    // 1. Run response interceptors
	    ProviderResponse processed = response;
	    for (ResponseInterceptor interceptor : interceptors) {
	        processed = interceptor.intercept(processed);
	    }

	    // 2. Run validators
	    boolean valid = true;
	    for (ResponseValidator validator : validators) {
	        if (!validator.validate(processed)) {
	            valid = false;
	            break;
	        }
	    }

	    // 3. Package into FrameworkResponse
	    FrameworkResponse frameworkResponse = new FrameworkResponse();
	    frameworkResponse.setOutputText(processed.getContent());
	    frameworkResponse.setValidated(valid);
	    frameworkResponse.setExecutionMetrics(Map.of(
	        "tokenUsage", processed.getTokenUsage()
	    ));
	    return frameworkResponse;
	}
}
