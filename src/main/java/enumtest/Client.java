package enumtest;

/**
 * Created by huishen on 17/4/19.
 */
public class Client {

    public static void main(String[] args) {

        for (BusinessType bizType : BusinessType.values()) {

            System.out.println(bizType);
        }

    }

}
