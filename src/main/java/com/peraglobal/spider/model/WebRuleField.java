package com.peraglobal.spider.model;

import java.io.Serializable;

public class WebRuleField implements Serializable {

	private static final long serialVersionUID = -1156028056553870021L;
	
	/**
	 * @category key
	 */
	private String fieldKey;
	
	/**
	 * @category 类型：regex、xpath、css、links
	 */
	private String fieldType;
	
	/**
	 * @category 内容
	 */
	private String fieldText;
	
	public String getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldText() {
		return fieldText;
	}

	public void setFieldText(String fieldText) {
		this.fieldText = fieldText;
	}

}
