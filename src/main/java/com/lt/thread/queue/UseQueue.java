package com.lt.thread.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;


public class UseQueue {

	public static void main(String[] args) throws Exception {
		/** 无阻塞无界的队列 先进先出 */
		ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<String>();
		/** add 和 offer 在  ConcurrentLinkedQueue 中无任何区别*/
		q.offer("a");
		q.offer("b");
		q.offer("c");
		q.add("d");
		System.out.println("取出并移除第一个元素：" + q.poll());
		System.out.println(q.size());
		System.out.println("取出第一个元素：" + q.peek());
		System.out.println(q.size());
		System.out.println("============================");
		
		/** 基于数组的有界阻塞队列 */
		ArrayBlockingQueue<String> array = new ArrayBlockingQueue<String>(2);
		array.add("a");		// 添加数据 如果成功返回 true 如果已满 抛出异常
		array.add("b");
//		array.add("cc");
		System.out.println(array.offer("c")); 	// 添加数据 如果成功返回 true 如果已满 返回false
//		array.put("d");	// 添加数据 如果成功返回 true 如果已满 则等待可用的空间
		array.poll();
		
		/** 无界阻塞队列 */
		LinkedBlockingQueue<String> link = new LinkedBlockingQueue<String>();
		link.add("a");
		link.add("b");
		link.add("c");
		link.add("d");
		System.out.println("===============");
		
		final SynchronousQueue<String> sync = new SynchronousQueue<String>();
		/*Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(sync.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t1.start();*/
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				sync.add("asdasd");
			}
		});
		t2.start();	
		
	}
}
