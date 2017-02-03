package com.lt.thread.queue;

public class Task  implements Comparable<Task>{
	private int id ;
	private String name;
	
	public Task(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int compareTo(Task task) {
		return this.id < task.id ? 1 : (this.id < task.id ? -1 : 0);  
	}
	
	public String toString(){
		return this.id + "," + this.name;
	}
}
