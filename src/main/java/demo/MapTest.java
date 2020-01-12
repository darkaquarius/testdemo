package demo;

import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by huishen on 17/2/10.
 */
public class MapTest {

    /**
     * map按照value排序
     */
    @Test
    public void temp() {
        TreeMap<String, Double> map = new TreeMap<>();

        map.put("A", 10.8);
        map.put("B", 2.8);
        map.put("C", 11.8);
        map.put("D", 3.8);
        map.put("E", 0.8);

        Map<String, Double> collect2 = map.entrySet()
                .stream().sorted(
                        Comparator.comparingDouble((Map.Entry<String, Double> entry) -> entry.getValue()).reversed()
                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existingValue, newValue) -> newValue, LinkedHashMap::new));
//                }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println();
    }

    @Test
    public void test() {
        Map<Integer, Person> map = new ConcurrentHashMap();
        map.put(1, new Person(1, "zhang", 20));
        map.put(2, new Person(2, "zhang", 30));

        //两次取同一个key,返回的value是同一个对象
        Person person1 = map.get(1);
        System.out.println(person1);
        System.out.println();
        Person person2 = map.get(2);
        System.out.println(person2);
        System.out.println();
        Person person3 = map.get(1);
        System.out.println(person3);

        Person person = map.get(3);
        System.out.println(person);
    }

    @Test
    public void test2() {
        Map<SimpApp, Integer> simpAppMap = new HashMap<>();
        simpAppMap.put(SimpApp.builder().appId("1").appName("zhang").build(), 10);
        simpAppMap.put(SimpApp.builder().appId("2").appName("wang").build(), 20);
        simpAppMap.put(SimpApp.builder().appId(null).appName(null).build(), 3);

        System.out.println(simpAppMap.get(SimpApp.builder().appId("1").build()));
        System.out.println(simpAppMap.get(SimpApp.builder().appId("2").build()));
        System.out.println(simpAppMap.get(SimpApp.builder().appId(null).build()));
    }

    @Test
    public void testKeySet() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        map.put("4", "d");
        Set<String> keySet1 = map.keySet();

        map.put("5", "e");
        Set<String> keySet2 = map.keySet();

        //比较两个keySet
        //每次调用keySet()方法,返回的是同一个实例!!!

        System.out.println(keySet1);
        System.out.println(keySet2);

    }

    /**
     * a1 = null
     * a2 = 1
     * a3 = 1
     */
    @Test
    public void testPutIfAbsent() {
        Map<String, Integer> map = new HashMap<>();
        Integer a1 = map.putIfAbsent("a", 1);
        System.out.println("a1 = " + a1);
        Integer a2 = map.putIfAbsent("a", 2);
        System.out.println("a2 = " + a2);
        Integer a3 = map.putIfAbsent("a", 3);
        System.out.println("a3 = " + a3);
    }

    /**
     * 如果不存在key，value置为1，
     * 如果存在key，value + 1
     */
    @Test
    public void testTmp() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.compute(1, (key, value) -> null == value ? 1 : value + 1);
        map.compute(4, (key, value) -> null == value ? 1 : value + 1);
    }

    @Test
    public void test10() throws Exception {
        String accessToken = "cf8c811a3e519529099f1c7a12dc3da3a1e7cadbb6c699a58b7ba3bb40f97d28";
        long timestamp = System.currentTimeMillis();
        String secret = "SEC939b4bfae62d962c42983862d5f8a5125a5319f6b2c7cdff2ae3b54ee801b036";
        String sign = generateSign(timestamp, secret);
        if (null == sign) {
            return;
        }

        String robotUrl = String.format(
                "https://oapi.dingtalk.com/robot/send?"
                        + "access_token=%s"
                        + "&timestamp=%s"
                        + "&sign=%s", accessToken, timestamp, sign);
        System.out.println(robotUrl);
    }

    private String generateSign(long timestamp, String secret) throws Exception {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
//        Base64.encodeBase64(signData)
        byte[] encode = Base64.getEncoder().encode(signData);
        return URLEncoder.encode(new String(encode),"UTF-8");
    }
}
