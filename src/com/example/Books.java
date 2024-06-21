package com.example;

public class Books {

	private int id;
	private String name;
	public Books() {}
	public Books(int id, String name) {
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
	public String toString() {
		// TODO Auto-generated method stub
		return "Book : " + getId() + " : " + getName();
	}
	
}
