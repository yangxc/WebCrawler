package com.peraglobal.web.model;

import java.io.Serializable;
import java.util.Date;

/**
 *  <code>Proxy.java</code>
 *  <p>功能：元数据存储类
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  @see 2016-12-19
 *  </br>最后修改人 无
 */
public class Proxy implements Serializable {

	private static final long serialVersionUID = -1474391355762769333L;
	
	/**
	 * @category ID
	 */
	private String proxyId;
	
	/**
	 * @category 爬虫 ID
	 */
	private String crawlerId;
	
	/**
	 * @category 代理服务器
	 */
	private String hostName;
	
	/**
	 * @category 端口
	 */
	private int port;

	/**
	 * @category 创建时间
	 */
	private Date createTime;
	
	/**
	 * @category 更新时间
	 */
	private Date updateTime;
	
	
	public String getProxyId() {
		return proxyId;
	}

	public void setProxyId(String proxyId) {
		this.proxyId = proxyId;
	}

	public String getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(String crawlerId) {
		this.crawlerId = crawlerId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
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
