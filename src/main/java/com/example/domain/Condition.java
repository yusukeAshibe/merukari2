package com.example.domain;

/**
 * conditionを扱うドメイン.
 * @author ashibe
 *
 */
public class Condition {

	/**
	 * ID
	 */
	private Integer id;
	
	/**
	 * condition名
	 */
	private String name;

	@Override
	public String toString() {
		return "Condition [id=" + id + ", name=" + name + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
