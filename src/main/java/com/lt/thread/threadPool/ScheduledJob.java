package com.lt.thread.threadPool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Temp extends Thread {
    public void run() {
        System.out.println("run");
    }
}

public class ScheduledJob {
	
    public static void main(String args[]) throws Exception {
    
    	Temp command = new Temp();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        /** 延时5S之后  1秒执行一次线程 */
        scheduler.scheduleWithFixedDelay(command, 5, 1, TimeUnit.SECONDS);
    
    }
}