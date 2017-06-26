package demo;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by huishen on 17/6/26.
 *
 */
public class PropertiesDemo {

    @Test
    public void test() {

        InputStream is = PropertiesDemo.class.getClassLoader().getResourceAsStream("test/test.properties");

        Properties prop = new Properties();
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String userName = prop.getProperty("user.name");
        String userCity = prop.getProperty("user.city");

        System.out.println(userName);
        System.out.println(userCity);

    }

}
