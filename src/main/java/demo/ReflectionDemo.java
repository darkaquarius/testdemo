// package demo;
//
// import java.lang.reflect.Field;
// import java.lang.reflect.ParameterizedType;
// import java.lang.reflect.Type;
// import java.util.List;
// import java.util.Map;
//
// /**
//  * @author huishen
//  * @date 18/2/6 下午2:00
//  */
// public class ReflectionDemo {
//
//     private String id;
//     private List<String> testStr;
//     private Map<String, String> testMap;
//
//     public static void main(String[] args) throws Exception {
//         // Field id = CoinMarketCapDto.class.getDeclaredField("id");
//         // assert id.getGenericType() instanceof ParameterizedType;
//         // Type genericType = id.getGenericType();
//         // System.out.println(genericType.getClass());    // Class
//         //
//         // Field testStr = CoinMarketCapDto.class.getDeclaredField("testStr");
//         // assert testStr.getGenericType() instanceof ParameterizedType;
//         // ParameterizedType genericType1 = (ParameterizedType) testStr.getGenericType();
//         // System.out.println(genericType1.getClass());       // ParameterizedTypeImpl
//         // System.out.println(genericType1.getRawType());     // List
//         // System.out.println(genericType1.getOwnerType());   // null
//         // Type[] arguments = genericType1.getActualTypeArguments();
//
//         Field testMap = CoinMarketCapDto.class.getDeclaredField("testMap");
//         assert testMap.getGenericType() instanceof ParameterizedType;
//         ParameterizedType genericType2 = (ParameterizedType) testMap.getGenericType();
//         System.out.println(genericType2.getClass());       // ParameterizedTypeImpl
//         System.out.println(genericType2.getRawType());     // List
//         System.out.println(genericType2.getOwnerType());   // null
//         Type[] arguments2 = genericType2.getActualTypeArguments();
//
//     }
// }
