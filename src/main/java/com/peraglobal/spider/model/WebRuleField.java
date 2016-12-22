package com.peraglobal.spider.model;

import java.io.Serializable;

public class WebRuleField implements Serializable {

	private static final long serialVersionUID = -1156028056553870021L;
	
	/**
	 * @category page 类型：addTargetRequests，putField
	 */
	private String type;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
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
