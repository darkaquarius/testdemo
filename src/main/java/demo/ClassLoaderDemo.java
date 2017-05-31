package demo;

import sun.misc.Launcher;

import java.net.URL;

/**
 * Created by huishen on 17/5/10.
 */
public class ClassLoaderDemo {

    public static void main(String[] args) {
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urLs.length; i++) {
            System.out.println(urLs[i].toExternalForm());
        }
    }

}
