package demo;

import org.junit.Test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileDemo {

    private static final String CLASS_FILE_SUFFIX = ".class";

    @Test
    public void test1() throws IOException {
        JarFile jarFile = new JarFile("/Library/Java/JavaVirtualMachines/jdk1.8.0_221.jdk/Contents/Home/jre/lib/rt.jar");
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            System.out.println(entries.nextElement());
        }

        System.out.println("\n\n");

        String name = "java.lang.String";
        String path = binaryNameToPath(name, false);
        JarEntry jarEntry = jarFile.getJarEntry(path);
        System.out.println(jarEntry.getName());
    }

    /**
     * 把类名(全类名)转换成path
     */
    private String binaryNameToPath(String binaryName, boolean withLeadingSlash) {
        // 1 for leading '/', 6 for ".class"
        StringBuilder path = new StringBuilder(7 + binaryName.length());
        if (withLeadingSlash) {
            path.append('/');
        }
        path.append(binaryName.replace('.', '/'));
        path.append(CLASS_FILE_SUFFIX);
        return path.toString();
    }

}
