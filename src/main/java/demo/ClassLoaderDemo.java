package demo;

import org.junit.Test;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by huishen on 17/5/10.
 *
 */
public class ClassLoaderDemo {

    @Test
    public void test() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> urls = null;
        try {
            urls = classLoader != null ? classLoader.getResources("META-INF/spring.factories") :
                ClassLoader.getSystemResources("META-INF/spring.factories");

            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(url));
                // String factoryClassNames = properties.getProperty(factoryClassName);
                // result.addAll(Arrays.asList(StringUtils.commaDelimitedListToStringArray(factoryClassNames)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> clazz = classLoader.loadClass("demo.ClassLoaderDemo");
        System.out.println(clazz.getClassLoader());
    }

}
