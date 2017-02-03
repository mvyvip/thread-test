package com.lt.thread.queue;

import java.util.Calendar;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Order implements Delayed {
	private int id;
	private String produce;
	private long endTime; /** 超时时间 秒 */
	
	public Order(int id, String produce, int endSecend) {
		super();
		this.id = id;
		this.produce = produce;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, endSecend);
		endTime = calendar.getTimeInMillis();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduce() {
		return produce;
	}
	public void setProduce(String produce) {
		this.produce = produce;
	}
	@Override
	public int compareTo(Delayed o) {
		Order order = (Order) o;
		return this.id < order.id ? 1 : (this.id == order.id ? 0 : -1);
	}
	@Override
	public long getDelay(TimeUnit unit) {
		return endTime - System.currentTimeMillis();
	}
	
}
