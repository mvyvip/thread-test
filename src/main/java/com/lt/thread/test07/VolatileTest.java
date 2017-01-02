package com.lt.thread.test07;

/**
 * Created by lt on 2017/1/2.
 */
public class VolatileTest extends Thread {

    /**
     * volatile
     * 用volatile修饰的变量，线程在每次使用变量的时候，都会读取变量修改后的最的值。volatile很容易被误用，用来进行原子性操作。
     * 不加的话 当flag设置为false的时候 t1线程还是会继续运行
     */
    private boolean flag = true;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        while(flag) {

        }
        System.out.println("over");
    }

    public static void main(String[] args) throws InterruptedException {
        final  VolatileTest vt = new VolatileTest();
        vt.start();
        Thread.sleep(1000);
        vt.setFlag(false);
    }
}
