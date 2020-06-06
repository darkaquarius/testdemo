package demo;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author huishen
 * @date 2019-04-30 17:21
 */
public class TmpTest {

    @Test
    public void test1() throws UnsupportedEncodingException {
//        String str1 = URLEncoder.encode("上海", "utf-8");
//        String str2 = new String(Base64.getEncoder().encode("上海".getBytes()));
        String str = URLEncoder.encode("sddf12_dce_12", "utf-8");
    }

    @Test
    public void test2() {
        HashMap<String, String> map = new HashMap<String, String>() {{
            put("business_album_permission_rpc_service", "business-album-permission-rpc-service");
        }};
        String xdcsName = "business_album_permission_rpc_service1";
        String cmdbName = map.get(xdcsName);
        if (StringUtils.isEmpty(cmdbName)) {
            cmdbName = xdcsName.replace("_", "-");
        }
        cmdbName = StringUtils.isEmpty(cmdbName) ? xdcsName.replace("_", "-") : cmdbName;
        System.out.println(cmdbName);
    }

    @Test
    public void test3() {
        int val = Integer.MIN_VALUE;
        int abs = Math.abs(val);
    }

    @Test
    public void test4() {
        String ip = "uat-sh-bs-3-b2-app-0-56.ximalaya.local";
        String s = ip.split("\\.")[0];
        System.out.println(s);
    }

    @Test
    public void test5() {
        boolean flag = true;
        flag &= true;
        flag &= false;
        System.out.println(flag);
    }

    @Test
    public void test6() {
        List<String> list = Arrays.asList("a", "b", "c");
        System.out.println(list);
    }

    @Test
    public void test7() {
        String url = "/hera-home/cache/xdcs_name_map";
        String pattern = "/hera-home/cache2";
        int i = url.indexOf(pattern);
        System.out.println(i);
    }

    @Test
    public void test8() {
        Map<String, Map<String, Integer>> map = new HashMap<>();
        Map<String, Integer> inner = new HashMap<>();
        inner.put("inner", 1);
        map.put("str", inner);
        System.out.println(map);
    }

}
