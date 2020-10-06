package com.classloader;

/**
 * 热加载测试类
 *
 * @author zt1994
 * @date 2020/10/6 16:12
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        new Thread(new MsgHandler()).start();
    }

}
