package httpClientdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huishen on 17/6/24.
 * post请求
 */
public class HttpPostDemo {

    // 用Map来模拟一个dto，先序列化，再转成StringEntity(HttpEntity)
    @Test
    public void test1() {
        //httpPost
        HttpPost httpPost = new HttpPost("http://loan.v1.guangjuhuizhan.cn/v1/users/login");
        httpPost.setHeader("Content-Type", "application/json");

        Map<String, String> map = new HashMap<>();
        map.put("mobile_phone", "13888888888");
        map.put("pwd", "123456");

        ObjectMapper objectMapper = new ObjectMapper();
        String s = null;
        try {
            s = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // StringEntity是HttpEntity的子类
        httpPost.setEntity(new StringEntity(s, "utf-8"));

        HttpClientUtil.execute(httpPost);
    }

    // HttpEntity
    // 不可以
    @Test
    public void test2() {
        StringBuffer sb = new StringBuffer().append("mobile_phone").append("=").append("13888888888").append("\n")
            .append("pwd").append("=").append("123456").append("\n");
        HttpEntity entity = new StringEntity(sb.toString(), Charset.defaultCharset());

        HttpPost httpPost = new HttpPost("http://loan.v1.guangjuhuizhan.cn/v1/users/login");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(entity);

        HttpClientUtil.execute(httpPost);
    }

    // request param
    // can not do it
    @Test
    public void test3() {
        HttpUriRequest request = RequestBuilder.post()
            .setUri("http://loan.v1.guangjuhuizhan.cn/v1/users/login")
            .setHeader("Content-Type", "application/json")
            .addParameter("mobile_phone", "13888888888")
            .addParameter("pwd", "123456")
            .build();

        HttpClientUtil.execute(request);

    }

}
