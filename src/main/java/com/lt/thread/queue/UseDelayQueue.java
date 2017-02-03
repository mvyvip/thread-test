package com.lt.thread.queue;

import java.util.concurrent.DelayQueue;

/**
 * 带有延迟时间的Queue，其中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素
 * 应用场景很多，比如对缓存超时的数据进行移除、 任务超时处理、空闲连接的关闭等等。 
 */
public class UseDelayQueue {
	public static void main(String[] args) throws Exception {
		DelayQueue<Order> q = new DelayQueue<Order>();
		q.add(new Order(1, "商品1", 3));
		q.add(new Order(3, "商品3", 3));
		q.add(new Order(2, "商品2", 3));
		Thread.sleep(4000);
		System.out.println(q.take().getId());
		System.out.println(q.take().getId());
		System.out.println(q.take().getId());
	}
}
