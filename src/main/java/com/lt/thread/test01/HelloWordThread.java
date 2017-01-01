package com.lt.thread.test01;

/**
 * 线程安全概念：当多个线程访问某一个类（对象或方法）时，这个对象始终都能表现出正确的行为，那么这个类（对象或方法）就是线程安全的。
 * synchronized：可以在任意对象及方法上加锁，而加锁的这段代码称为"互斥区"或"临界区"
 */
public class HelloWordThread extends Thread{

    private int count = 5;

    /**
     * 多线程同时访问这个变量的时候，count可能值可能会不对，比如t1进入了 count-- 的代码，这时候 count = 4，
     * 这时并未打印后面的count = xx， 第二个线程（t2）进来了，count又 -- 了，然后t1和t2分别执行后面的打印，就会出现两次=3
     * 要线程安全的话就给run方法加一把锁（synchronized），可以把它理解为代码块，只允许一个线程执行该代码块。
     */
    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + ", count = " + count);
    }

    public static void main(String[] args) {
        HelloWordThread thread = new HelloWordThread();

        Thread t1 = new Thread(thread,"t5");
        Thread t2 = new Thread(thread,"t5");
        Thread t3 = new Thread(thread,"t5");
        Thread t4 = new Thread(thread,"t5");
        Thread t5 = new Thread(thread,"t5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
