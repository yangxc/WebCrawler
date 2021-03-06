package com.peraglobal.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peraglobal.web.model.History;
import com.peraglobal.web.model.Proxy;
import com.peraglobal.web.model.Rule;
import com.peraglobal.web.model.Web;

import us.codecraft.webmagic.Spider;

import com.alibaba.fastjson.JSON;
import com.peraglobal.spider.model.WebRule;
import com.peraglobal.spider.process.SpiderManager;
import com.peraglobal.spider.process.WebProcessor;

/**
 *  <code>SpiderService.java</code>
 *  <p>功能:数据库爬虫功能入口 Service
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  2016-12-15
 *  </br>最后修改人 无
 */
@Service("spiderService")
public class SpiderService {

	@Autowired
   	private WebService webService;
	
	@Autowired
   	private ProxyService proxyService;
	
	@Autowired
   	private HistoryService historyService;
	
	
	/**
	 * 开始爬虫
	 * @param crawler
	 * @throws Exception 
	 */
	public void start(Web web) throws Exception {
		
		// 查询规则文件
		Rule rule = webService.getRule(web.getCrawlerId());
		WebRule webRule = JSON.parseObject(rule.getExpress(), WebRule.class);
		
		// 查询代理
		Proxy proxy = proxyService.getProxyByCrawlerId(web.getCrawlerId());
		// 创建数据库导入对象
		Spider
			.create(new WebProcessor(web).setWebRule(webRule).setProxy(proxy))
			.addUrl(webRule.getUrl())
			.setWeb(web)
			.register();
		// 开始爬虫操作
		SpiderManager.start(web.getCrawlerId());
		
		// 添加监控信息，历史记录生成一条新记录
		History history = new History();
		history.setCrawlerId(web.getCrawlerId());
		historyService.createHistory(history);
	}

	/**
	 * 停止爬虫
	 * @param crawler
	 */
	public void stop(Web web) {
		// 停止爬虫
		SpiderManager.stop(web.getCrawlerId());
		
		// 销毁爬虫
		SpiderManager.remove(web.getCrawlerId());
		
		// 修改监控信息，历史记录生成停止时间
		historyService.stopHistory(web.getCrawlerId());
		
	}

}