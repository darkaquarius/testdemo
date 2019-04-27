package demo;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author huishen
 * @date 2019-04-08 16:40
 */
public class InetAddressDemo {

    /**
     * 最终是到Inet4AddressImpl#lookupAllHostAddr(String hostname)方法，
     * 该方法是一个native方法。
     */
    @Test
    public void test() throws UnknownHostException {
        String host = "itunes.apple.com";
        InetAddress address = InetAddress.getByName(host);
        InetAddress[] addresses = InetAddress.getAllByName(host);
        System.out.println();
    }

}
