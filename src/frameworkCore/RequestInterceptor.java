package frameworkCore;

public interface RequestInterceptor {
	public boolean intercept(UserRequest request, RequestContext context);
}
