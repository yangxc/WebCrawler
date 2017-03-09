package com.peraglobal.mongodb.model;

import java.io.Serializable;
import java.util.Date;

import com.mongodb.DBObject;

/**
 *  <code>Metadata.java</code>
 *  <p>功能：元数据存储类
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  @see 2016-12-19
 *  </br>最后修改人 无
 */
public class Metadata implements Serializable {

	private static final long serialVersionUID = -5577310925375978878L;

	/**
	 * @category ID
	 */
	private String id;

	/**
	 * @category 爬虫 ID
	 */
	private String crawlerId;
	
	/**
	 * @category md5 码
	 */
	private String md;
	
	/**
	 * @category URL 地址
	 */
	private String url;
	
	/**
	 * @category HTML 元数据
	 */
	private byte[] htmlMeta;
	
	/**
	 * @category 数据是否完整：0.完整; 1.不完整
	 */
	private String isFull;
	
	/**
	 * @category 元数据 json 格式
	 */
	private String metadata;
	
	/**
	 * @category 元数据 key value 格式
	 */
	private DBObject kvs;
	
	/**
	 * @category 创建时间
	 */
	private Date createTime;
	
	/**
	 * @category 更新时间
	 */
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(String crawlerId) {
		this.crawlerId = crawlerId;
	}

	public String getMd() {
		return md;
	}

	public void setMd(String md) {
		this.md = md;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public byte[] getHtmlMeta() {
		return htmlMeta;
	}

	public void setHtmlMeta(byte[] htmlMeta) {
		this.htmlMeta = htmlMeta;
	}

	public String getIsFull() {
		return isFull;
	}

	public void setIsFull(String isFull) {
		this.isFull = isFull;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public DBObject getKvs() {
		return kvs;
	}

	public void setKvs(DBObject kvs) {
		this.kvs = kvs;
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
