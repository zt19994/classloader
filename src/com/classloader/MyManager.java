package com.classloader;

/**
 * 自定义manager,BaseManager的子类，需要实现Java类的热加载功能
 *
 * @author zt1994
 * @date 2020/10/5 22:53
 */
public class MyManager implements BaseManager {

    /**
     * logic方法
     */
    @Override
    public void logic() {
        System.out.println("学习java热加载!!");
    }
}
