import java.util.concurrent.TimeUnit;

public class RateLimiter {
    private final long capacity;
    private long tokens;
    private long lastRefillTime;
    private final long refillRate;

    public RateLimiter(long capacity, long refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRate = refillRatePerSecond;
        this.tokens = capacity;
        this.lastRefillTime = System.nanoTime();
    }

    public synchronized boolean allowRequest() {
        refillTokens();
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    private void refillTokens() {
        long now = System.nanoTime();
        long elapsedTime = now - lastRefillTime;
        long tokensToAdd = (elapsedTime * refillRate) / TimeUnit.SECONDS.toNanos(1);
        if (tokensToAdd > 0) {
            tokens = Math.min(capacity, tokens + tokensToAdd);
            lastRefillTime = now;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter(5, 1);
        for (int i = 0; i < 10; i++) {
            System.out.println("Request " + (i + 1) + ": " + rateLimiter.allowRequest());
            Thread.sleep(500);
        }
    }
}
