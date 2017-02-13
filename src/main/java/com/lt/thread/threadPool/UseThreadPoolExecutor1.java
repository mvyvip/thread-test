package com.lt.thread.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

public class UseThreadPoolExecutor1 {

	public static void main(String[] args) {
		/**
		 * 如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
		 * 
		 * 如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
		 * 
		 * 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，
		 * 建新的线程来处理被添加的任务。
		 * 
		 * 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，
		 * 那么通过handler所指定的策略来处理此任务。也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、
		 * 最大线程maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
		 * 
		 * 当线程池中的线程数量大于
		 * corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
		 * 
		 * 
		 * 无界队列相当于没有拒绝策略
		 * 
		 * 
		 */
		ThreadPoolExecutor pool = new ThreadPoolExecutor(1, // coreSize
				2, // MaxSize
				60, // 60
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3) // 指定一种队列
																		// （有界队列）
				// new LinkedBlockingQueue<Runnable>()
				, new MyRejected()
		// , new DiscardOldestPolicy()
		);

		MyTask mt1 = new MyTask(1, "任务1");
		MyTask mt2 = new MyTask(2, "任务2");
		MyTask mt3 = new MyTask(3, "任务3");
		MyTask mt4 = new MyTask(4, "任务4");
		MyTask mt5 = new MyTask(5, "任务5");
		MyTask mt6 = new MyTask(6, "任务6");

		pool.execute(mt1);
		pool.execute(mt2);
		pool.execute(mt3);
		pool.execute(mt4);
		pool.execute(mt5);
		pool.execute(mt6);

		pool.shutdown();

	}
}
