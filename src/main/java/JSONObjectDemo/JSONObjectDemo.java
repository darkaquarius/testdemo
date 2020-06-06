package JSONObjectDemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONObjectDemo {

    @Test
    public void test1() {
        JSONObject jsonObject = new JSONObject();

        List<HostProfile> hostProfileList = new ArrayList<>();
        hostProfileList.add(new HostProfile("127.0.0.1", 8080));
        hostProfileList.add(new HostProfile("127.0.0.1", 8081));

        Map<String, List<HostProfile>> notNormalHostListMap = new HashMap<>();
        List<HostProfile> list = new ArrayList<>();
        list.add(new HostProfile("127.0.0.2", 8080));
        list.add(new HostProfile("127.0.0.2", 8081));
        notNormalHostListMap.put("uat2", list);

        Map<String, HostProfile> hostMap = new HashMap<>();
        hostMap.put("127.0.0.3:8080", new HostProfile("127.0.0.3", 8080));
        hostMap.put("127.0.0.3:8081", new HostProfile("127.0.0.3", 8081));


        Map<String, Object> appLoadBalancer = new HashMap<String, Object>(3);
        appLoadBalancer.put("strategy", "ronbin");
        appLoadBalancer.put("hosts", hostProfileList);
        appLoadBalancer.put("notNormalHosts", notNormalHostListMap);
        appLoadBalancer.put("hostMap", hostMap);
        jsonObject.put("business-album-presale-web", appLoadBalancer);


        SouthgateApiResponse southgateApiResponse = new SouthgateApiResponse();
        southgateApiResponse.setData(jsonObject);

        // 序列化
        String responseStr = JSON.toJSONString(southgateApiResponse);

        // 反序列化
        SouthgateApiResponse response = JSONObject.parseObject(responseStr, SouthgateApiResponse.class);
        System.out.println(response);

    }

}
