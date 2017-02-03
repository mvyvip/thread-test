package com.lt.thread.queue;

import java.util.concurrent.PriorityBlockingQueue;

public class UsePriorityBlockingQueue  {
	public static void main(String[] args) throws InterruptedException {
		PriorityBlockingQueue<Task> q = new PriorityBlockingQueue<Task>();
		q.offer(new Task(3, "name = 3"));
		q.offer(new Task(1, "name = 1"));
		q.offer(new Task(2, "name = 2"));
		
		System.out.println(q.take());
		System.out.println(q.take());
		System.out.println(q.take());
		System.out.println(q.take());
	}
}
