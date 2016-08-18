package com.devoteam.tracker.util;

import java.util.Iterator;
import java.util.Set;

public class Option extends HTMLElement {
	
	private boolean selected;
	
	public Option() {
		super("option");
		selected = false;
	}

	public Option(boolean selected) {
		super("option");
		this.selected = selected;
	}
	
	public Option(String text, String value, boolean selected) {
		super("option");
		attributes.put("value", value);
		this.value = new StringBuilder(text);
		this.selected = selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String toString() {
		StringBuilder elem = new StringBuilder("<" + type);
		Set<String> keys = attributes.keySet();
		for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
			String name = it.next();
			String val = attributes.get(name);
			elem.append(" " + name + "=" + dQ + val + dQ);
		}
		if (selected) {
			elem.append(" selected");
		}
		elem.append(">");
		if (value.length() > 0) elem.append(value.toString());
		elem.append("</" + type + ">");
		return elem.toString();
	}
}
