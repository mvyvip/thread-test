package com.lt.thread.test02;

/**
 * 每个对象都有各自的一把锁，如果想锁住多个对象的一个方法的话，方法前面加 synchronized static 即可
 * 在静态方法上加synchronized关键字，表示锁定.class类，类一级别的锁（独占.class类）
 */
public class MultiThread {

    private static int num = 0;

    public static synchronized void print(String tag) {
        try {
            if("a".equals(tag)) {
                num = 100;
                System.out.println("tag a set num over, num = " + num);
                Thread.sleep(1000);
            } else {
                num = 200;
                System.out.println("tag b set num over, num = " + num);
            }
            System.out.println("tag " + tag + ", num = " + num);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //俩个不同的对象
        final MultiThread m1 = new MultiThread();
        final MultiThread m2 = new MultiThread();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                m1.print("a");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                m2.print("b");
            }
        });

        t1.start();
        t2.start();
    }
}
