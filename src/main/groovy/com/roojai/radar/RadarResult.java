package com.roojai.radar;

import groovy.transform.ToString;

@ToString(includeNames = true, includeFields = true, excludes = "metaClass,class")
public class RadarResult {
	private Object root;
	private String masterSetId;
	private String errorCode;
	private String errorMessage;
	private Integer finalValue;
	private Long id;

	public Object getRoot() {
		return root;
	}

	public void setRoot(Object root) {
		this.root = root;
	}

	public String getMasterSetId() {
		return masterSetId;
	}

	public void setMasterSetId(String masterSetId) {
		this.masterSetId = masterSetId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getfinalValue() {
		return finalValue;
	}

	public void setfinalValue(Integer finalValue) {
		this.finalValue = finalValue;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RadarResult [root=" + root + ", masterSetId=" + masterSetId + ", errorCode=" + errorCode
				+ ", errorMessage=" + errorMessage + ", finalValue=" + finalValue + "]";
	}

}
