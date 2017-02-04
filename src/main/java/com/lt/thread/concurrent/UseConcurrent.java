package com.lt.thread.concurrent;

import java.util.concurrent.Executors;

public class UseConcurrent {
	public static void main(String[] args) {
		/**
		 * 该方法返回一个固定数量的线程池，该方法的线程数始终不变，
		 * 当有一个任务提交时，若线程池中空闲，则立即执行，若没有，
		 * 则会被暂缓在一个任务队列中等待有空闲的线程去执行。
		 */
		Executors.newFixedThreadPool(10);
		
		/**
		 * 创建一个线程的线程池，若空闲则执行，若没有空闲线程则暂缓在任务列队中。
		 */
		Executors.newSingleThreadExecutor();
		
		/**
		 * 返回一个可根据实际情况调整线程个数的线程池，不限制最大线程数量，若用空闲的线程则执行任务，
		 * 若无任务则不创建线程。并且每一个空闲线程会在60秒后自动回收。
		 */
		Executors.newCachedThreadPool();
		
		/**
		 * 方法返回一个SchededExecutorService对象，但该线程池可以指定线程的数量。
		 */
		Executors.newScheduledThreadPool(10);
	}
}
