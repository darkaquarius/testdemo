package httpClientdemo;

/**
 * Created by huishen on 17/6/21.
 * httpclient使用socks5代理
 */

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class HttpClientDemo03 {

    // 依次是代理地址，代理端口号，用户密码
    private static String proxyHost = "127.0.0.1";
    private static int proxyPort = 1080;
    private static String proxyName = "user";
    private static String proxyPwd = "123456";


    public static String getWithProxy(String url, Map<String, String> headers, String charset) {
        //用户名和密码验证
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                PasswordAuthentication p = new PasswordAuthentication(proxyName, proxyPwd.toCharArray());
                return p;
            }
        });

        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", new MyConnectionSocketFactory())
            .register("https", new MySSLConnectionSocketFactory(SSLContexts.createSystemDefault())).build();
        // FakeDnsResolver!
        // PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg, new FakeDnsResolver());
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);
        CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
        try {
            InetSocketAddress socksaddr = new InetSocketAddress(proxyHost, proxyPort);
            HttpClientContext context = HttpClientContext.create();
            context.setAttribute("socks.address", socksaddr);
            HttpGet httpget = new HttpGet(url);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpget.setHeader(key, headers.get(key));
                }
            }
            CloseableHttpResponse response = httpclient.execute(httpget, context);
            try {
                return new String(EntityUtils.toByteArray(response.getEntity()), charset);
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    static class FakeDnsResolver implements DnsResolver {
        @Override
        public InetAddress[] resolve(String host) throws UnknownHostException {
            // Return some fake DNS record for every request, we won't be using it
            return new InetAddress[]{InetAddress.getByAddress(new byte[]{1, 1, 1, 1})};
        }
    }

    static class MyConnectionSocketFactory extends PlainConnectionSocketFactory {
        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        @Override
        public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
                                    InetSocketAddress localAddress, HttpContext context) throws IOException {
            // Convert address to unresolved
            InetSocketAddress unresolvedRemote = InetSocketAddress
                .createUnresolved(host.getHostName(), remoteAddress.getPort());
            return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
        }
    }

    static class MySSLConnectionSocketFactory extends SSLConnectionSocketFactory {

        public MySSLConnectionSocketFactory(final SSLContext sslContext) {
            // You may need this verifier if target site's certificate is not secure
            // super(sslContext, ALLOW_ALL_HOSTNAME_VERIFIER);
            super(sslContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        }

        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        @Override
        public Socket connectSocket(int connectTimeout, Socket socket, HttpHost host, InetSocketAddress remoteAddress,
                                    InetSocketAddress localAddress, HttpContext context) throws IOException {
            // Convert address to unresolved
            InetSocketAddress unresolvedRemote = InetSocketAddress
                .createUnresolved(host.getHostName(), remoteAddress.getPort());
            return super.connectSocket(connectTimeout, socket, host, unresolvedRemote, localAddress, context);
        }
    }


    // 测试一下
    public static void main(String[] args) {
        try {
            String url="http://www.google.com";
            Map<String, String> headers = new HashMap<String, String>();
            // headers.put("Accept", "text/html, */*; q=0.01");
            // headers.put("Accept-Encoding", "gzip, deflate, sdch");
            // headers.put("Accept-Language", "zh-CN,zh;q=0.8");
            // headers.put("Referer", url);
            // headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:46.0) Gecko/20100101 Firefox/46.0");

            String result=HttpClientDemo03.getWithProxy(url, headers, "UTF-8");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
