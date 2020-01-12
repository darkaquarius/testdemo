package demo;

import static com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility.getBytes;

public class ClassLoaderDemo2 extends ClassLoader {

    private String name;

    public ClassLoaderDemo2(ClassLoader parent, String name) {
        super(parent);
        this.name = name;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

//        ClassLoader parentClassLoader = getSystemClassLoader();              // AppClassLoader
        ClassLoader parentClassLoader = getSystemClassLoader().getParent();    // ExtClassLoader

        Class<?> clazz = null;
        try {
            clazz = parentClassLoader.loadClass(name);
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }

        if (clazz != null) {
            return clazz;
        }

        clazz = findClass(name);
        return clazz;
    }

    /**
     * loadClass(String name) 方法向上没有加载到类的时候，会调用此方法
     * 需要子类自己来实现
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = getBytes("/Users/xmly/Documents/git/testdemo/src/main/java/demo/ClassLoaderDemo2.java");
        Class<?> clazz = this.defineClass(name, bytes, 0, bytes.length);
        return clazz;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoaderDemo2 classLoaderDemo2 = new ClassLoaderDemo2(ClassLoaderDemo2.class.getClassLoader(), "MyClassLoaderDemo");
        Class<?> clazz = classLoaderDemo2.loadClass("demo.ClassLoaderDemo2");
        System.out.println(clazz);
    }


}
