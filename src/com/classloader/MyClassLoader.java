package com.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 自定义Java类加载器来实现Java类的热加载
 *
 * @author zt1994
 * @date 2020/10/5 18:39
 */
public class MyClassLoader extends ClassLoader {

    /**
     * 要加载的Java类的classpath路径
     */
    private String classpath;

    public MyClassLoader(String classpath) {
        super(ClassLoader.getSystemClassLoader());
        this.classpath = classpath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }


    /**
     * 加载class文件中的内容
     *
     * @param name
     * @return
     */
    private byte[] loadClassData(String name) {
        try {
            name = name.replace(",", "//");
            FileInputStream fileInputStream = new FileInputStream(new File(classpath + name + ".class"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int b = 0;
            while ((b = fileInputStream.read()) != -1) {
                byteArrayOutputStream.write(b);
            }
            fileInputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
