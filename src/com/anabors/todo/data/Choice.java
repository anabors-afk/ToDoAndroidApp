package com.anabors.todo.data;

import java.util.UUID;

public class Choice {
	
	private String id;
	private String name;
	
	public Choice(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Choice() {
    	this.id = UUID.randomUUID().toString();
    }

	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString()
	{
		return this.getName();
	}
}
