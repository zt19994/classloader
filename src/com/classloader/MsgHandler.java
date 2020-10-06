package com.classloader;

/**
 * 后台线程检测类
 * <p>
 * 后台启动一条线程不断刷新重新加载实现了热加载的类
 *
 * @author zt1994
 * @date 2020/10/6 16:08
 */
public class MsgHandler implements Runnable {


    @Override
    public void run() {
        while (true) {
            BaseManager manager = ManagerFactory.getManager(ManagerFactory.MY_MANAGER);
            manager.logic();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
