package SampleApp;

import frameworkCore.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		final String userInput = "What courses for this semester have I not received grades for yet?";
		final String userId = "UID1067";
		final Map<String, Object> metadata = Map.of(
			    "sessionId", "SESS-001",
			    "source", "console",
			    "timestamp", System.currentTimeMillis(),
			    "appName", "UniversityAdvisor"
			);
		
		UserRequest request = new UserRequest(userInput, userId, metadata);
		
		DynamicContext userProfile = new DynamicContext(
				"userProfile", 
				Map.of("role", "customer", "preferredLanguage", "english"),
				LocalDateTime.now(),
				3600L
				);
		DynamicContext conversationHistory = new DynamicContext(
				"conversationHistory",
				List.of("How can I see my pass grades?"),
				LocalDateTime.now(),
				1800L
				);
		
		StaticContext systemInstructions = new StaticContext(
				"systemInstructions",
				"You are a university academic advisor. Only answer questions related to academics.",
				"config/System Instructions.txt"
				);
		
		DomainContext domainRules = new DomainContext(
				"domainRules",
				"Students may only register for courses they have prerequisites for.",
				"SCHEMA-ID-001"
				);
		
		
		final List<ContextElement> contextElements = new ArrayList<>();
		contextElements.add(domainRules);
		contextElements.add(systemInstructions);
		contextElements.add(conversationHistory);
		contextElements.add(userProfile);
		
		
		RequestContext context = new RequestContext(contextElements);
		PromptBuilder promptBuilder = new PromptBuilder();
		SecurityInterceptor securityInterceptor = new SecurityInterceptor(
			    List.of("DROP TABLE", "<script>", "ignore previous instructions"),  // blockedPatterns
			    List.of("customer", "admin", "guest")                               // allowedUserRoles
			);
		
		RequestPipelineManager requestPipeline = new RequestPipelineManager(
			    List.of(securityInterceptor),
			    List.of(),  // no context providers needed since context is already built
			    promptBuilder
			);
		
		
		
		ResponsePipelineManager responsePipeline = new ResponsePipelineManager(
			    List.of(new ContentFilterInterceptor(), new FormattingInterceptor()),
			    List.of(new StructureValidator())
			);
		
		LLMAdapter adapter = new GeminiAdapter();
		LLMFramework framework = new LLMFramework(requestPipeline, responsePipeline, adapter);
		
		// 4. Show prompt
		System.out.println("=== Your Prompt ===");
		System.out.println(userInput);
		System.out.println();
				
		// 5. Process the request
		FrameworkResponse response = framework.processRequest(request);
		
		// 6. Print the result
		System.out.println();
		System.out.println("=== Ecommerce Bot Response ===");
		System.out.println("Output: " + response.getOutputText());
		System.out.println("Validated: " + response.isValidated());
		System.out.println("Metrics: " + response.getExecutionMetrics());
					
	}
}

// Full marks yo?⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀

//⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣾⠀
//⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⢂⠀⠀⠀⠀⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⠀
//⠀⠀⠀⠀⠀⠀⢀⡈⠛⠦⠀⠀⠀⠆⠀⠀⠀⠀⡀⠀⠐⠀⠀⠀⠀⠀⠀⡛⢱⣿⣿⠀
//⠀⠀⠀⠠⣤⣴⠝⢛⠂⠀⠀⣐⣤⣠⣤⣰⣤⣜⢀⠀⢀⠀⠀⠀⠀⠀⠀⡅⣸⣿⣿⠀
//⠀⠀⠀⠀⢖⣂⠄⡀⣤⣶⣿⣿⣿⣿⣿⣿⣿⣿⣷⣦⡀⠀⠁⠀⠀⠀⠀⣇⣿⣿⣿⠀
//⠀⠀⠀⠀⣤⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢿⣿⣿⣶⣦⠀⠀⠀⠀⢹⠹⣿⣿⠀
//⠀⠀⠀⣤⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢟⣽⡪⠽⠛⡛⠻⣿⣷⠠⠀⠀⠀⠀⢻⣿⠀
//⠀⠀⠀⢦⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠁⣠⣶⣾⣷⣤⡘⢟⡣⠀⠀⠀⠀⠘⣿⠀
//⠀⠀⠀⡞⠿⢿⣿⣿⣿⣿⡿⣿⡿⠋⠀⣠⠾⣻⣽⠾⠻⣿⣿⡜⡷⡀⠀⠀⠀⢶⣿⠀
//⠀⠀⠀⢠⣶⣤⣄⣀⠉⠻⢿⣶⡏⣡⡾⠗⣩⡀⣀⣶⣶⣾⣿⣿⡸⣧⠀⣢⠄⠸⣿⠀
//⠀⠀⠀⠘⣿⠿⠛⠋⠋⠝⡃⣿⣿⣬⡻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡄⢰⡹⣼⠀⢿⠀
//⠀⠀⠀⠀⣯⡰⣗⣴⣾⣿⢡⢹⣿⣎⢷⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⡁⢸⣛⡟⠀⣾⠀
//⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⡏⢸⣿⣿⣎⠿⣿⣿⣿⣿⣿⣿⣿⡿⢹⣿⠀⠉⠀⣴⣿⠀
//⠀⠀⠀⠀⠸⣿⣿⣿⣿⣿⡟⣿⣿⣿⣿⣿⡌⣿⣿⣿⣿⣿⡟⢠⣿⣿⠀⠀⠀⠉⢸⠀
//⠀⠀⠀⠀⠀⠹⣿⣿⣿⣿⣏⠌⠋⣩⡶⣒⣵⣿⣿⣿⣿⣿⣷⣿⣿⣿⠀⠀⠀⠀⢸⠀
//⠀⠀⠀⠀⠀⠀⠈⠙⢛⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⣻⣿⣿⣿⣿⡟⡄⢀⠀⠀⠈⠀
//⠀⠀⠀⠀⠀⠀⠀⠀⠐⢝⢿⣿⡛⢯⡶⢶⣒⣛⣧⣾⢿⣿⣿⣿⡿⢡⣿⠈⢧⡀⠀⠀
//⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⡐⣶⣾⣿⣿⣿⠿⣻⣻⣿⣿⣿⡿⢡⣿⣿⡇⠆⢧⠀⠀
