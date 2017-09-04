package httpClientdemo;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * Created by huishen on 17/9/2.
 *
 */

// CloseableHttpClient httpclient = HttpClients.custom()
//     .setRetryHandler(myRetryHandler)
//     .build();

// HttpRequestRetryHandler接口只有一个方法
public class MyRetryHandler implements HttpRequestRetryHandler {

    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {

        if (executionCount >= 5) {
            // do NOT retry if over max retry count
            return false;
        }

        if (exception instanceof InterruptedIOException) {
            // Timeout
            return false;
        }

        if (exception instanceof UnknownHostException) {
            // unknown host
            return false;
        }

        if (exception instanceof ConnectTimeoutException) {
            // Connection refused
            return false;
        }

        if (exception instanceof SSLException) {
            // SSL handshake exception
            return false;
        }

        HttpClientContext clientContext = HttpClientContext.adapt(context);
        HttpRequest request = clientContext.getRequest();
        // 判断是否幂等
        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
        if (idempotent) {
            // retry if the request is considered idempotent
            return true;
        }

        return false;
    }
}
