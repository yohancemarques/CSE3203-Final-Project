package frameworkCore;

public interface FrameworkAspect {
	public void before(Object target, Object[] args);
	public void after(Object target, Object result);
	public void onError(Object target, Throwable error);
	
}
