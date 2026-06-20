package frameworkCore;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PerformanceMonitoringAspect implements FrameworkAspect {
	private Map<String, Long> timers = new ConcurrentHashMap<>();
	
	
	
	@Override
	public void before(Object target, Object[] args) {
		String key = target.getClass().getSimpleName();
        timers.put(key, System.nanoTime());
	}

	@Override
	public void after(Object target, Object result) {
		String key = target.getClass().getSimpleName();
        Long start = timers.remove(key);
        if (start != null) {
            long elapsedMs = (System.nanoTime() - start) / 1_000_000;
            System.out.println("[PERF] " + key + " took " + elapsedMs + " ms");
        }

	}

	@Override
	public void onError(Object target, Throwable error) {
		String key = target.getClass().getSimpleName();
        timers.remove(key); // clean up so a failed stage doesn't leave a stale timer
        System.out.println("[PERF] " + key + " failed after error: " + error.getMessage());
	}

}
