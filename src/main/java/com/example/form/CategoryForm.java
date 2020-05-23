package com.example.form;

public class CategoryForm {
	
	private String parent;

	@Override
	public String toString() {
		return "CategoryForm [parent=" + parent + "]";
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
