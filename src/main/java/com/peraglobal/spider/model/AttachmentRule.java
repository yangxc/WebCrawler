package com.peraglobal.spider.model;

import java.io.Serializable;

/**
 *  <code>Attachment.java</code>
 *  <p>功能:附件下载规则
 *  
 *  <p>Copyright 安世亚太 2017 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  @see 2017-2-27
 */
public class AttachmentRule implements Serializable {

	private static final long serialVersionUID = -7304119678303682276L;

	/**
	 * @category 附件地址
	 */
	private String attachmentKey;
	
	/**
	 * @category 附件类型
	 */
	private String attachmentType;
	
	
	public String getAttachmentKey() {
		return attachmentKey;
	}
	public void setAttachmentKey(String attachmentKey) {
		this.attachmentKey = attachmentKey;
	}
	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
}
