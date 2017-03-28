package com.cqut.permission.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Role implements Serializable {
	private int id;
	private String name;
	private String description;
	
	private List<Function> functions = new ArrayList<Function>();

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	
}
