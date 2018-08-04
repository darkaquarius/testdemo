package demo;

import org.junit.Test;

import java.net.InetAddress;

/**
 * @author huishen
 * @date 18/6/25 上午11:31
 */

public class HostNameDemo {

    @Test
    public void getHostName() throws Exception {
        String hostName = InetAddress.getLocalHost().getHostName();
        System.out.println(hostName);
        System.out.println(System.getenv("COMPUTERNAME"));
    }

}
