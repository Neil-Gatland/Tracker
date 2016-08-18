package com.devoteam.tracker.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HTMLElement {
	
	protected String type;
	protected StringBuilder value;
	protected Map<String, String> attributes = new HashMap<String, String>();
	protected final char dQ = 34; 
	protected String checked = "";
	protected String disabled = "";
	protected String readOnly = "";
	
	public HTMLElement(String type) {
		this.type = type;
		this.value = new StringBuilder();
	}

	public HTMLElement(String type, String elementClass) {
		this.type = type;
		attributes.put("class", elementClass);
		this.value = new StringBuilder();
	}

	public HTMLElement(String type, String elementClass, String value) {
		this.type = type;
		this.value = new StringBuilder(value);
		attributes.put("class", elementClass);
	}
	
	public HTMLElement(String type, String style, String elementClass, 
		String value) {
		this.type = type;
		this.value = new StringBuilder(value);
		attributes.put("style", style);
		attributes.put("class", elementClass);
	}
		
	public HTMLElement(String type, String id, String style, String elementClass, 
			String value) {
			this.type = type;
			this.value = new StringBuilder(value);
			attributes.put("id", id);
			attributes.put("style", style);
			attributes.put("class", elementClass);
		}

	public HTMLElement(String type, String id, String name, String inputType, 
			String value, String onClick) {
			this.type = type;
			attributes.put("id", id);
			attributes.put("name", name);
			attributes.put("type", inputType);
			attributes.put("value", value);
			attributes.put("onClick", onClick);
			this.value = new StringBuilder();
		}

	public HTMLElement(String type, String id, String style, String elementClass, 
		String onClick,	String onMouseOver, String onMouseOut, String value) {
		this.type = type;
		this.value = new StringBuilder(value);
		attributes.put("id", id);
		attributes.put("style", style);
		attributes.put("class", elementClass);
		attributes.put("onClick", onClick);
		attributes.put("onMouseOver", onMouseOver);
		attributes.put("onMouseOut", onMouseOut);
	}
		
	public HTMLElement(String type, String id, String style, String elementClass, 
		String onClick,	String onMouseOver, String onMouseOut, String value,
		String title) {
		this.type = type;
		this.value = new StringBuilder(value);
		attributes.put("id", id);
		attributes.put("style", style);
		attributes.put("class", elementClass);
		attributes.put("onClick", onClick);
		attributes.put("onMouseOver", onMouseOver);
		attributes.put("onMouseOut", onMouseOut);
		attributes.put("title", title);
	}
		
	public void setAttribute(String name, String val) {
		attributes.put(name, val);
	}
	
	public void setValue(String value) {
		this.value = new StringBuilder(value);
	}
	
	public void appendValue(String value) {
		this.value.append(value);
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked?" checked":"";
	}
	
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly?" readonly":"";
	}
	
	public String toString() {
		StringBuilder elem = new StringBuilder("<" + type);
		Set<String> keys = attributes.keySet();
		for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
			String name = it.next();
			String val = attributes.get(name);
			elem.append(" " + name + "=" + dQ + val + dQ);
		}
		elem.append(checked);
		elem.append(readOnly);
		elem.append(">");
		if (value.length() > 0) elem.append(value.toString());
		if ((!type.equalsIgnoreCase("input")) && (!type.equalsIgnoreCase("img"))) elem.append("</" + type + ">");
		return elem.toString();
	}
}
