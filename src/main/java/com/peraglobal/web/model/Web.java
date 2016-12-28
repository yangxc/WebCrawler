package com.peraglobal.web.model;

import java.io.Serializable;
import java.util.Date;

public class Web implements Serializable {

	private static final long serialVersionUID = 7269171837908367794L;

	/**
	 * @category 采集 ID
	 */
	private String crawlerId;
	
	/**
	 * @category 采集名称
	 */
	private String crawlerName;
	
	/**
	 * @category 组 ID
	 */
	private String groupId;
	
	/**
	 * @category 组名称
	 */
	private String groupName;
	
	/**
	 * @category 表达式
	 */
	private String express;
	
	/**
	 * @category 状态
	 */
	private String state;
	
	/**
	 * @category 创建时间
	 */
	private Date createTime;
	
	/**
	 * @category 更新时间
	 */
	private Date updateTime;


	public String getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(String crawlerId) {
		this.crawlerId = crawlerId;
	}
	
	public String getCrawlerName() {
		return crawlerName;
	}

	public void setCrawlerName(String crawlerName) {
		this.crawlerName = crawlerName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
