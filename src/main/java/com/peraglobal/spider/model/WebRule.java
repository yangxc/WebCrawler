package com.peraglobal.spider.model;

import java.io.Serializable;
import java.util.List;

public class WebRule implements Serializable {

	private static final long serialVersionUID = -8606521947569860193L;
	
	/**
	 * @category 编码
	 */
	private String coding;
	
	/**
	 * @category 重试次数
	 */
	private int retryTimes;
	
	/**
	 * @category 抓取间隔
	 */
	private int sleepTime;
	
	/**
	 * @category 开启线程数量
	 */
	private int thread;
	
	/**
	 * @category 主url
	 */
	private String url;
	
	private String listUrl;
	
	private String listUrlType;
	
	private String detailUrl;
	
	private String detailUrlType;
	
	/**
	 * @category 规则对象
	 */
	private List<WebRuleField> webRuleFields;
	
	/**
	 * @category 附件下载规则
	 */
	private AttachmentRule attachmentRule;
	

	public String getCoding() {
		return coding;
	}

	public void setCoding(String coding) {
		this.coding = coding;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public int getThread() {
		return thread;
	}

	public void setThread(int thread) {
		this.thread = thread;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getListUrl() {
		return listUrl;
	}

	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}

	public String getListUrlType() {
		return listUrlType;
	}

	public void setListUrlType(String listUrlType) {
		this.listUrlType = listUrlType;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getDetailUrlType() {
		return detailUrlType;
	}

	public void setDetailUrlType(String detailUrlType) {
		this.detailUrlType = detailUrlType;
	}

	public List<WebRuleField> getWebRuleFields() {
		return webRuleFields;
	}

	public void setWebRuleFields(List<WebRuleField> webRuleFields) {
		this.webRuleFields = webRuleFields;
	}
	
	public AttachmentRule getAttachmentRule() {
		return attachmentRule;
	}

	public void setAttachmentRule(AttachmentRule attachmentRule) {
		this.attachmentRule = attachmentRule;
	}
	
}