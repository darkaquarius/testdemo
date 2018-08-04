package httpClientdemo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by huishen on 17/6/22.
 * 最简单的HttpClient请求
 */
public class HttpClientDemo01 {

    // 可以使http请求，也可以是https请求
    @Test
    public void test01() throws IOException {

        // String url = "https://www.baidu.com";
        String url = "https://api.binance.com/api/v1/klines?symbol=BTCUSDT&interval=15m";
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        CloseableHttpResponse response = httpClient.execute(httpGet);

        int statusCode = response.getStatusLine().getStatusCode();
        String respStr = EntityUtils.toString(response.getEntity(), Charset.defaultCharset());
        System.out.println(statusCode);
        System.out.println(respStr);
        response.close();
    }

    // fluent api
    @Test
    public void test02() throws IOException {
        HttpResponse response = Request.Get("http://www.baidu.com")
            .connectTimeout(1000)
            .socketTimeout(1000)
            .execute().returnResponse();
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(EntityUtils.toString(response.getEntity(), Charset.defaultCharset()));
    }

    // fluent api
    // handleResponse
    @Test
    public void test03() throws IOException {
        Request.Get("http://www.baidu.com")
            .execute().handleResponse( response -> {
                StatusLine statusLine = response.getStatusLine();
                HttpEntity entity = response.getEntity();
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
                }
                if (null == entity) {
                    throw new ClientProtocolException("Response contains no content");
                }
                DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();

                try {
                    DocumentBuilder documentBuilder = dbfac.newDocumentBuilder();
                    ContentType contentType = ContentType.getOrDefault(entity);
                    if (!contentType.equals(ContentType.APPLICATION_XML)) {
                        throw new ClientProtocolException("unexpected content type:".concat(contentType.toString()));
                    }
                    Charset charset = contentType.getCharset();
                    if (null == charset) {
                        charset = HTTP.DEF_CONTENT_CHARSET;
                    }
                    return documentBuilder.parse(entity.getContent(), charset.toString());
                } catch (ParserConfigurationException e) {
                    throw new IllegalStateException(e);
                } catch (SAXException e) {
                    throw new ClientProtocolException("Malformed XML document", e);
                }
            }
        );
    }


}
