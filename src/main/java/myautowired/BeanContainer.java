package myautowired;

import java.lang.reflect.Field;

/**
 * Created by huishen on 17/5/9.
 */
public class BeanContainer {

    public static Object getInstance(String className) {

        try {
            Class<?> clazz = Class.forName(className);
            Object bean = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if(field.isAnnotationPresent(MyAutowired.class)) {
                    Class<?> type = field.getType();
                    Object value = type.newInstance();
                    field.setAccessible(true);
                    field.set(bean, value);
                }
            }
            return bean;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
