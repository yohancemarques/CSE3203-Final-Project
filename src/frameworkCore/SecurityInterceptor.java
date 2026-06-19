package frameworkCore;
import java.util.List;
import java.util.Map;


public class SecurityInterceptor implements RequestInterceptor {
	private List<String> blockedPatterns;
    private List<String> allowedUserRoles;
    
    public SecurityInterceptor(List<String> blockedPatterns, List<String> allowedUserRoles) {
        this.blockedPatterns = blockedPatterns;
        this.allowedUserRoles = allowedUserRoles;
    }
	
	@Override
	public boolean intercept(UserRequest request, RequestContext context) {
		// 1. Check if user's role is allowed
        ContextElement userProfile = context.getElementbyKey("userProfile");
        if (userProfile != null) {
            Map<String, Object> profileData = (Map<String, Object>) userProfile.getValue();
            String role = (String) profileData.get("role");
            if (!allowedUserRoles.contains(role)) {
                System.out.println("[SECURITY] Blocked: unauthorized role -> " + role);
                return false;
            }
        }

        // 2. Scan input for blocked patterns
        String input = request.getUserInput().toLowerCase();
        for (String pattern : blockedPatterns) {
            if (input.contains(pattern.toLowerCase())) {
                System.out.println("[SECURITY] Blocked: suspicious pattern detected -> " + pattern);
                return false;
            }
        }

        System.out.println("[SECURITY] Request cleared.");
        return true;
	}

}
