// package httpClientdemo;
//
// /**
//  * Created by huishen on 17/6/21.
//  * Socks Proxy
//  * 一个sock代理服务器
//  */
//
// import org.apache.http.Header;
// import org.apache.http.HttpHost;
// import org.apache.http.HttpResponse;
// import org.apache.http.client.ClientProtocolException;
// import org.apache.http.client.config.RequestConfig;
// import org.apache.http.client.config.RequestConfig.Builder;
// import org.apache.http.client.methods.HttpRequestBase;
// import org.apache.http.client.methods.RequestBuilder;
// import org.apache.http.client.protocol.HttpClientContext;
// import org.apache.http.config.Registry;
// import org.apache.http.config.RegistryBuilder;
// import org.apache.http.conn.ConnectTimeoutException;
// import org.apache.http.conn.socket.ConnectionSocketFactory;
// import org.apache.http.impl.client.CloseableHttpClient;
// import org.apache.http.impl.client.HttpClientBuilder;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
// import org.apache.http.protocol.HttpContext;
//
// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.net.InetSocketAddress;
// import java.net.Proxy;
// import java.net.Socket;
// import java.net.SocketTimeoutException;
// import java.util.HashMap;
// import java.util.Map;
//
// public class SocksProxyDemo {
//
//     private HttpClientBuilder httpClientBuilder = HttpClients.custom();
//     private CloseableHttpClient httpClient = null;
//
//     private Builder requestConfigBuilder = null;
//     private RequestConfig requestConfig = null;
//
//     private RequestBuilder requestBuilder = null;
//     private HttpRequestBase request = null;
//
//     private HttpResponse httpResponse = null;
//
//     private boolean useProxy = false;
//
//     private String proxyHost = "127.0.0.1";
//     private int proxyPort = 1080;
//
//     public SocksProxyDemo(String requestUri, String requestMethod) {
//         requestConfigBuilder = RequestConfig.custom();
//         requestBuilder = RequestBuilder.create(requestMethod);
//         requestBuilder.setUri(requestUri);
//     }
//
//     public SocksProxyDemo setRequestHeader(String name, String value) {
//         requestBuilder.addHeader(name, value);
//         return this;
//     }
//
//     public SocksProxyDemo setRquestHeaders(HashMap<String, String> headers) {
//         for (Map.Entry<String, String> entry : headers.entrySet()) {
//             requestBuilder.addHeader(entry.getKey(), entry.getValue());
//         }
//
//         return this;
//     }
//
//     public SocksProxyDemo setRquestParameter(String name, String value) {
//         requestBuilder.addParameter(name, value);
//         return this;
//     }
//
//     public SocksProxyDemo setRequestParamters(HashMap<String, String> paramters) {
//         for (Map.Entry<String, String> entry : paramters.entrySet()) {
//             requestBuilder.addParameter(entry.getKey(), entry.getValue());
//         }
//
//         return this;
//     }
//
//     /**
//      * 因为只想用本地的 socket5 代理, 所以不需要输入 host, 只要提供本地 socket5 代理的端口即可
//      *
//      * @param port
//      *            本地 socket5 代理端口
//      * @return
//      */
//     public SocksProxyDemo setRequestProxy(int port) {
//         useProxy = true;
//         proxyPort = port;
//
//         Registry<ConnectionSocketFactory> reg = RegistryBuilder
//             .<ConnectionSocketFactory>create()
//             .register("http", new LocalConnectionSocket()).build();
//
//         PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
//             reg);
//
//         cm.setMaxTotal(10);
//
//         httpClientBuilder.setConnectionManager(cm);
//
//         return this;
//     }
//
//     public RequestConfig getRequestConfig() {
//         if (requestConfig == null)
//             requestConfig = requestConfigBuilder.build();
//
//         return requestConfig;
//     }
//
//     public HttpRequestBase getRequest() {
//         if (request == null) {
//             request = (HttpRequestBase) requestBuilder.build();
//             request.setConfig(requestConfig);
//         }
//
//         return request;
//     }
//
//     public void execute() throws IOException, ClientProtocolException {
//         close();
//         httpClient = httpClientBuilder.build();
//         if (useProxy) {
//             InetSocketAddress socksaddr = new InetSocketAddress(proxyHost,
//                 proxyPort);
//             HttpClientContext context = HttpClientContext.create();
//             context.setAttribute("socks.address", socksaddr);
//
//             httpResponse = httpClient.execute(getRequest(), context);
//         } else {
//             httpResponse = httpClient.execute(getRequest());
//         }
//     }
//
//     public Header getResponseHeader(String headerName) {
//         if (httpResponse != null) {
//             Header[] headers = httpResponse.getAllHeaders();
//
//             for (Header header : headers) {
//                 if (header.getName().equals(headerName))
//                     return header;
//             }
//         }
//
//         return null;
//     }
//
//     public Header[] getResponseHeaders() {
//         return httpResponse != null ? httpResponse.getAllHeaders() : null;
//     }
//
//     public HttpResponse getHttpResponse() {
//         return httpResponse;
//     }
//
//     public String getHttpResponseAsString(String charset) throws IOException {
//         charset = (charset == null || charset.isEmpty()) ? "UTF-8" : charset;
//
//         InputStream in = getHttpResponse().getEntity().getContent();
//         BufferedReader reader = new BufferedReader(new InputStreamReader(in,
//             charset));
//
//         StringBuffer sBuffer = new StringBuffer();
//         String line = null;
//
//         while ((line = reader.readLine()) != null) {
//             sBuffer.append(line);
//         }
//
//         return sBuffer.toString();
//     }
//
//     public void close() throws IOException {
//         if (httpClient != null) {
//             httpClient.close();
//             httpResponse = null;
//         }
//     }
//
//     private static class LocalConnectionSocket implements
//         ConnectionSocketFactory {
//
//         @Override
//         public Socket createSocket(final HttpContext context)
//             throws IOException {
//             InetSocketAddress socksaddr = (InetSocketAddress) context
//                 .getAttribute("socks.address");
//             Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
//             return new Socket(proxy);
//         }
//
//         @Override
//         public Socket connectSocket(final int connectTimeout,
//                                     final Socket socket, final HttpHost host,
//                                     final InetSocketAddress remoteAddress,
//                                     final InetSocketAddress localAddress, final HttpContext context)
//             throws IOException {
//             Socket sock;
//             if (socket != null) {
//                 sock = socket;
//             } else {
//                 sock = createSocket(context);
//             }
//             if (localAddress != null) {
//                 sock.bind(localAddress);
//             }
//             try {
//                 sock.connect(remoteAddress, connectTimeout);
//             } catch (SocketTimeoutException ex) {
//                 throw new ConnectTimeoutException(ex, host,
//                     remoteAddress.getAddress());
//             }
//             return sock;
//         }
//     }
//
//     public static void main(String[] args) {
//         try {
//             SocksProxyDemo socksProxyDemo = new SocksProxyDemo("http://www.google.com", "GET");
//             socksProxyDemo.setRequestProxy(1080);
//             socksProxyDemo.execute();
//
//             for (Header header : socksProxyDemo.getResponseHeaders()) {
//                 System.out
//                     .println(header.getName() + " : " + header.getValue());
//             }
//
//             System.out.println(socksProxyDemo.getHttpResponseAsString(null));
//
//             socksProxyDemo.close();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
