package demo;

import java.io.File;
import java.util.Arrays;

/**
 * Created by huishen on 16/10/18.
 */
public class FileTest {

    public static void main(String args[]){
        File dest = new File("/Users/huishen");
        String[] fileList = dest.list();
        Arrays.stream(fileList).forEach(file -> {
            System.out.println(file);
        });
    }
}
