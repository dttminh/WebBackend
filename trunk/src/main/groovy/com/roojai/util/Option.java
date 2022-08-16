package com.roojai.util;

public class Option {
	private String labelTh;
	private String labelEn;
	private Object value;
	
	public Option(String labelTh,String labelEn,Object value){
		this.labelTh = labelTh;
		this.labelEn = labelEn;
		this.value = value;
	}
	
	public String getLabelTh() {
		return labelTh;
	}
	

	public void setLabelTh(String labelTh) {
		this.labelTh = labelTh;
	}
	

	public String getLabelEn() {
		return labelEn;
	}
	

	public void setLabelEn(String labelEn) {
		this.labelEn = labelEn;
	}
	

	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
