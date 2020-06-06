package guavademo;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author huishen
 * @date 2019-08-11 10:11
 *
 *
 * guava RateLimiter有2种模式：
 *     SmoothBursty: 令牌生成速度恒定
 *     SmoothWarmingUp: 令牌生成速度缓慢提升直到维持在一个稳定值
 *
 */
public class RateLimiterDemo {

    /**
     * cutTime=1535439657427 acq:1 waitTime:0.0
     * cutTime=1535439658431 acq:3 waitTime:0.997045
     * cutTime=1535439661429 acq:5 waitTime:2.993028
     * cutTime=1535439666426 acq:7 waitTime:4.995625
     * cutTime=1535439673426 acq:9 waitTime:6.999223
     *
     * RateLimiter支持预消费，比如在acquire(5)时，等待时间是3秒，是上一个获取令牌时预消费了3个令牌
     */
    @Test
    public void test1() {
        // SmoothBuisty稳定模式
        RateLimiter rateLimiter = RateLimiter.create(1);

        for(int i = 1; i < 10; i = i + 2) {
            double waitTime = rateLimiter.acquire(i);
            System.out.println("cutTime=" + System.currentTimeMillis() + " acq:" + i + " waitTime:" + waitTime);
        }

    }

    @Test
    public void test2() {
        int permitsPerSecond = 100;
        RateLimiter rateLimiter = RateLimiter.create(permitsPerSecond);
        if (((int)rateLimiter.getRate()) != permitsPerSecond) {
            rateLimiter.setRate(permitsPerSecond);
        }

        permitsPerSecond = 200;
        if (((int)rateLimiter.getRate()) != permitsPerSecond) {
            rateLimiter.setRate(permitsPerSecond);
        }

    }

    @Test
    public void test3() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(1);
        Thread.sleep(3);
        
    }


}
