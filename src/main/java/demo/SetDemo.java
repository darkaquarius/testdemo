package demo;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by huishen on 17/3/7.
 */
public class SetDemo {

    @Test
    public void test(){
        Set<String> keywords = new HashSet<>();
        keywords.add("a");
        keywords.add("b");
        keywords.add("c");
        keywords.add("d");
        keywords.add("e");

        Set<String> relativeKeywords = new HashSet<>();
        relativeKeywords.add("a");
        relativeKeywords.add("b");
        relativeKeywords.add("h");
        relativeKeywords.add("I");
        relativeKeywords.add("j");
        relativeKeywords.add("k");

        int size = keywords.size();

        int relativeSize = relativeKeywords.size();
        relativeKeywords.removeAll(keywords);
        int restSize = relativeKeywords.size();
        if(((relativeSize - restSize) / (double)size) > 0.3){
            System.out.println("true");
        }
    }

}
