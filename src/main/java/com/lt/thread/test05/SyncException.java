package com.lt.thread.test05;

/**
 * Created by lt on 2017/1/1.
 */
public class SyncException {

    public void oper() {

            int i = 0;
            while(true) {
                try {
                i++;
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " , i = " + i);
                if(i == 10){
                    Integer.parseInt("a");
                    //                    throw new RuntimeException();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final SyncException se = new SyncException();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                se.oper();
            }
        },"t1");
        t1.start();
    }

}
