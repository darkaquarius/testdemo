package demo;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * Created by huishen on 16/12/27.
 */
public class BeanUitlsTest {

    @Test
    public void test1() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        User user = User.builder().friends(Arrays.asList("shen", "wang")).build();
        String values = BeanUtils.getProperty(user, "friends[1]");
        System.out.println(values);
    }

    @Test
    public void test2() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        User.Car car = User.Car.builder().brand("benz").price("30").build();
        User user = User.builder().car(car).build();
        String car1 = BeanUtils.getProperty(user, "car");
        System.out.println(car1);
    }
}
