package com.peraglobal.spider.model;

import com.peraglobal.web.model.Proxy;
import com.peraglobal.web.model.Web;

import java.io.Serializable;

/**
 *  <code>Proxy.java</code>
 *  <p>功能：互联网采集
 *  
 *  <p>Copyright 安世亚太 2017 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  @see 2017-2-24
 *  </br>最后修改人 无
 */
public class WebCrawler extends Web implements Serializable {

	private static final long serialVersionUID = -214309247271059256L;

	/**
	 * @category 采集规则
	 */
	private WebRule webRule;
	
	/**
	 * @category 代理
	 */
	private Proxy proxy;


	public WebRule getWebRule() {
		return webRule;
	}

	public void setWebRule(WebRule webRule) {
		this.webRule = webRule;
	}
	
	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	
}
