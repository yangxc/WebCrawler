package com.peraglobal.spider.model;

import com.peraglobal.web.model.Proxy;
import com.peraglobal.web.model.Web;

public class WebCrawler extends Web {

	private static final long serialVersionUID = -316900112629226818L;
	
	private WebRule webRule;
	
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
