// Features implemented:
// 1. User prompt input
// 2. Context modelling through ContextProvider (user metadata, domain rules, system instructions, conversation history)
// 3. Request pipeline: security interception and prompt building
// 4. Real API calling with Gemini AI
// 5. Response pipeline: content filtering, formatting, structure validation
// 6. Performance monitoring aspect

package SampleApp;

import frameworkCore.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		
		// 1. Store user prompt, metadata and user id into a single object
		System.out.println("=== University Advisor Bot ===");
		System.out.println("Enter a prompt\n");
		String userInput = scanner.nextLine();
		final String userId = "UID1067";
		final Map<String, Object> metadata = Map.of(
			    "sessionId", "SESS-001",
			    "source", "console",
			    "timestamp", System.currentTimeMillis(),
			    "appName", "UniversityAdvisor"
			);
		
		UserRequest request = new UserRequest(userInput, userId, metadata);
		
		
		//2. Create context elements and save in an array
		DynamicContext userProfile = new DynamicContext(
				"userProfile", 
				Map.of("role", "student", "preferredLanguage", "english"),
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
		
		//3. Build prompt, run security checks
		RequestContext context = new RequestContext(contextElements);
		PromptBuilder promptBuilder = new PromptBuilder();
		SecurityInterceptor securityInterceptor = new SecurityInterceptor(
			    List.of("DROP TABLE", "<script>", "ignore previous instructions"),  // blockedPatterns
			    List.of("student", "admin", "guest")                               // allowedUserRoles
			);
		
		// 4. Construct request and response pipeline managers 
		ContextProvider staticProvider = (req) -> contextElements;
		RequestPipelineManager requestPipeline = new RequestPipelineManager(
			    List.of(securityInterceptor),
			    List.of(staticProvider),  
			    promptBuilder
			);
		
		ResponsePipelineManager responsePipeline = new ResponsePipelineManager(
			    List.of(new ContentFilterInterceptor(), new FormattingInterceptor()),
			    List.of(new StructureValidator())
			);
		
		// 5. Choose LLM, send pipeline managers and LLMAdapter to LLM framework
		// For now, only Gemini has been tested and proven to work
		// Go to GeminiAdapter.java to read more about implementation and add an API key
		final String selectedProvider = "gemini"; // options: "gemini", "claude", "openai", "local"
		 
		LLMAdapter adapter = switch (selectedProvider) {
			case "gemini" -> new GeminiAdapter();
			case "claude" -> new ClaudeAdapter();
			case "openai" -> new OpenAIAdapter();
			case "local"  -> new LocalModelAdapter();
			default -> throw new IllegalArgumentException("Unknown LLM provider: " + selectedProvider);
		};
		LLMFramework framework = new LLMFramework(requestPipeline, responsePipeline, adapter);
		
		// 6. Show prompt
		System.out.println("=== Your Prompt ===");
		System.out.println(userInput);
		System.out.println();
				
		// 7. Process the request
		FrameworkResponse response = framework.processRequest(request);
		
		// 8. Print the result
		System.out.println();
		System.out.println("=== University Advisor Response ===");
		System.out.println("Output: " + response.getOutputText());
		System.out.println("Validated: " + response.isValidated());
		System.out.println("Metrics: " + response.getExecutionMetrics());
		System.out.println("\n\n\n\n=== App Closed ===");
				
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
