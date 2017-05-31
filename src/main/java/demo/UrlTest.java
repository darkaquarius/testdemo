package demo;

import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by huishen on 16/11/14.
 */
public class UrlTest {

    @Test
    public void test1(){
        String connectUrl = "http://www.baidu.com";

        String word = "my world,words,'word";

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/datas/export_all")
            .queryParam("word", word);

        String url = connectUrl + builder.toUriString();
        System.out.println(url);
        // byte[] bytes = new RestTemplate().getForObject(url, byte[].class);

    }

}
