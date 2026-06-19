package frameworkCore;
import java.time.*;

public class DynamicContext extends ContextElement{
	private LocalDateTime timeStamp;
	private long ttl;
	
	
	
	public DynamicContext(String key, Object value, LocalDateTime timeStamp, long ttl) {
		super(key, value);
		this.timeStamp = timeStamp;
		this.ttl = ttl; // time to live in seconds
	}



	public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = this.timeStamp.plusSeconds(this.ttl);
        return now.isAfter(expirationTime);
    }
}
