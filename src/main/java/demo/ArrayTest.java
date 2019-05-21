package demo;

import org.junit.Test;

/**
 * @author huishen
 * @date 2018-12-14 11:40
 */
public class ArrayTest {

    /**
     * generic array creation error
     * <p>
     * http://www.tothenew.com/blog/why-is-generic-array-creation-not-allowed-in-java/
     */
    @Test
    public void gegericArray() {
        // List<Integer> arrayOfIdList[] = new ArrayList<Integer>[10];// Suppose generic array creation is legal.
        // List<String> nameList = new ArrayList<String>();
        // Object objArray[] = arrayOfIdList; // that is allowed because arrays are covariant
        // objArray[0] = nameList;
        // Integer id = objArray[0].get(0);
    }

    @Test
    public void testArray() {
        char[] array = new char[]{'a', 'b'};
        System.out.println(array[0]);
        System.out.println(array[1]);
    }

}
