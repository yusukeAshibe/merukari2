package com.example.form;

public class SearchForm {
	
	private String id;
	
	private String name;
	
	private String parent;
	
	private  String chuCategory=null;
	
	public  Integer parseInt(String parent) {
	 return new Integer(parent);
	}
	
	public Integer parseInt2(String chuCategory) {
		return new Integer(chuCategory);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	

	public String getChuCategory() {
		return chuCategory;
	}

	public void setChuCategory(String chuCategory) {
		this.chuCategory = chuCategory;
	}

	@Override
	public String toString() {
		return "SearchForm [id=" + id + ", name=" + name + ", parent=" + parent + ", chuCategory=" + chuCategory + "]";
	}

	

	

	
	

}
