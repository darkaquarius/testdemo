package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author huishen
 * @date 18/4/22 下午7:19
 */
@RunWith(value = Parameterized.class)
public class ParameterizedTest {

    private String a;
    private String b;
    private String c;

    public ParameterizedTest(String a, String b, String c){
        this.a = a;
        this.b = b;
        this.c = c;
    };

    @Parameterized.Parameters
    public static Collection<String []> getParams(){
        return Arrays.asList(new String [][]{
            {"c","d","cd"},
            {"a","b","ab"},
        });
    }

    @Test
    public void test(){
        assertEquals(re(a,b), c);
    }

    public String re(String a,String b){
        return a+b;
    }

}
