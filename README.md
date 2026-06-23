# CSE3203-Final-Project


## Setup
- Open and IDE that supports Java maven projects and create a new project.
- Copy the SampleApp and frameworCore folders into the source folder of the project.
- Place the pom.xml file in the root of the project folder.
- Add a Gemini API key to the project environment through the IDE or through terminal.
- Build and run the app and follow the instructions in the terminal window.


## Structure
```
CSE3203 Project Sample App/
├── src/
│   ├── frameworkCore/
│   │   ├── APIContextProvider.java
│   │   ├── CachedContextProvider.java
│   │   ├── ClaudeAdapter.java
│   │   ├── ContentFilterInterceptor.java
│   │   ├── ContextElement.java
│   │   ├── ContextProvider.java
│   │   ├── DatabaseContextProvider.java
│   │   ├── DomainContext.java
│   │   ├── DynamicContext.java
│   │   ├── FormattingInterceptor.java
│   │   ├── FrameworkAspect.java
│   │   ├── FrameworkResponse.java
│   │   ├── GeminiAdapter.java
│   │   ├── LLMAdapter.java
│   │   ├── LLMFramework.java
│   │   ├── LocalModelAdapter.java
│   │   ├── LoggingAndAuditingAspect.java
│   │   ├── OpenAIAdapter.java
│   │   ├── PerformanceMonitoringAspect.java
│   │   ├── PromptBuilder.java
│   │   ├── ProviderResponse.java
│   │   ├── RequestContext.java
│   │   ├── RequestInterceptor.java
│   │   ├── RequestPipelineManager.java
│   │   ├── ResponseInterceptor.java
│   │   ├── ResponsePipelineManager.java
│   │   ├── ResponseValidator.java
│   │   ├── SecurityInterceptor.java
│   │   ├── StaticContext.java
│   │   ├── StructureValidator.java
│   │   ├── UserRequest.java
│   ├── SampleApp/
│   │   ├── main.java
├── README.md
└── pom.xml
```


## References
GeeksforGeeks. (2025, July 12). How to validate SSN (Social Security Number) using Regular Expression. GeeksforGeeks. https://www.geeksforgeeks.org/dsa/how-to-validate-ssn-social-security-number-using-regular-expression/

Java SDK. (n.d.). Claude API Docs. https://platform.claude.com/docs/en/cli-sdks-libraries/sdks/java

Lanka, B. S. (n.d.). How to validate an email using Regex in Java | Bala Subramanyam Lanka. https://balasubramanyamlanka.com/how-to-validate-an-email-using-regex-in-java/

My Regex tutorial. (n.d.). Gist. https://gist.github.com/arundvp/188d92fefda9bb7546ee52a9ecf7aad6

Using Gemini API keys. (n.d.). Google AI for Developers. https://ai.google.dev/gemini-api/docs/api-key
