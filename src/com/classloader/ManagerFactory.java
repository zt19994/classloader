package com.classloader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 加载manager的工厂
 *
 * @author zt1994
 * @date 2020/10/6 15:49
 */
public class ManagerFactory {

    /**
     * 记录热加载类的加载信息
     */
    private static final Map<String, LoadInfo> loadTimeMap = new HashMap<>();

    /**
     * 要加载的类的classpath
     */
    public static final String CLASS_PATH = "F:/develop/classloader/out/production/classloader/";

    /**
     * 实现热加载的类的全名称（包名+类名）
     */
    public static final String MY_MANAGER = "com.classloader.MyManager";

    /**
     * 获取manager
     *
     * @param className
     * @return
     */
    public static BaseManager getManager(String className) {
        File loadFile = new File(CLASS_PATH + className.replaceAll("\\.", "/") + ".class");
        long lastModified = loadFile.lastModified();
        // loadTimeMap不包含className，即类没有被加载，需加载类到JVM
        if (loadTimeMap.get(className) == null) {
            load(className, lastModified);
        } else if (loadTimeMap.get(className).getLoadTime() != lastModified) {
            // 加载类的时间戳变了，要重新加载类到JVM
            load(className, lastModified);
        }
        return loadTimeMap.get(className).getManager();
    }


    /**
     * 加载类到JVM中
     *
     * @param className
     * @param lastModified
     */
    private static void load(String className, long lastModified) {
        MyClassLoader myClassLoader = new MyClassLoader(CLASS_PATH);
        Class<?> loadClass = null;
        try {
            loadClass = myClassLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        BaseManager manager = newInstance(loadClass);
        LoadInfo loadInfo = new LoadInfo(myClassLoader, lastModified);
        loadInfo.setManager(manager);
        loadTimeMap.put(className, loadInfo);
    }


    /**
     * 以反射的方式创建BaseManager子类对象
     *
     * @param loadClass
     * @return
     */
    private static BaseManager newInstance(Class<?> loadClass) {
        try {
            return (BaseManager) loadClass.getConstructor(new Class[]{}).newInstance(new Object[]{});
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

}
