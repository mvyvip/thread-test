package com.lt.thread.test08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * wait notfiy 方法，wait释放锁，notfiy不释放锁
 *
 * @author alienware
 */
public class ListAdd2 {
    private volatile static List list = new ArrayList();

    public void add() {
        list.add("test");
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) throws InterruptedException {

        final ListAdd2 list2 = new ListAdd2();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
//        final Object lock = new Object();


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    synchronized (lock) {
                        for (int i = 0; i < 10; i++) {
                            list2.add();
                            System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
                            Thread.sleep(500);
                            if (list2.size() == 5) {
                                System.out.println("已经发出通知..");
//                                lock.notify();
                                countDownLatch.countDown();
                            }
//                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                synchronized (lock) {
                    if (list2.size() != 5) {
                        try {
//                            lock.wait();
                            countDownLatch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("当前线程：" + Thread.currentThread().getName() + "收到通知线程停止..");
                    throw new RuntimeException();
//                }
            }
        }, "t2");

        t2.start();
        Thread.sleep(500);
        t1.start();

    }

}
