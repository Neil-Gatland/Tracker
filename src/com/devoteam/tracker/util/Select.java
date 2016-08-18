package com.devoteam.tracker.util;

public class Select extends HTMLElement {
	
	public Select(String name) {
		super("select");
		this.setAttribute("name", name);
		this.setAttribute("id", name);
	}

	public Select(String name, String selectClass) {
		super("select");
		this.setAttribute("name", name);
		this.setAttribute("id", name);
		this.setAttribute("class", selectClass);
	}

}
