package httpClientdemo;

import org.apache.http.client.cache.CacheResponseStatus;
import org.apache.http.client.cache.HttpCacheContext;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClients;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by huishen on 17/11/22.
 *
 * http cache
 *
 */
public class HttpCacheDemo {

    @Test
    public void test1() throws IOException {
        CacheConfig cacheConfig = CacheConfig.custom()
            .setMaxCacheEntries(1000)
            .setMaxObjectSize(8192)
            .build();
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(30000)
            .setSocketTimeout(30000)
            .build();
        CloseableHttpClient cachingClient = CachingHttpClients.custom()
            .setCacheConfig(cacheConfig)
            .setDefaultRequestConfig(requestConfig)
            .build();

        HttpCacheContext context = HttpCacheContext.create();
        HttpGet httpget = new HttpGet("http://www.mydomain.com/content/");
        CloseableHttpResponse response = cachingClient.execute(httpget, context);
        try {
            CacheResponseStatus responseStatus = context.getCacheResponseStatus();
            switch (responseStatus) {
                case CACHE_HIT:
                    System.out.println("A response was generated from the cache with " +
                        "no requests sent upstream");
                    break;
                case CACHE_MODULE_RESPONSE:
                    System.out.println("The response was generated directly by the " +
                        "caching module");
                    break;
                case CACHE_MISS:
                    System.out.println("The response came from an upstream server");
                    break;
                case VALIDATED:
                    System.out.println("The response was generated from the cache " +
                        "after validating the entry with the origin server");
                    break;
                default:
                    break;
            }
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
