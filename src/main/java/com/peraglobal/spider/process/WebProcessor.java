package com.peraglobal.spider.process;

import java.util.List;

import com.peraglobal.spider.model.WebConst;
import com.peraglobal.spider.model.WebRule;
import com.peraglobal.spider.model.WebRuleField;
import com.peraglobal.web.model.Web;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爬虫实现类
 * @author yongqian.liu
 *
 */
public class WebProcessor implements PageProcessor {

	Web web;
	WebRule webRule;
	List<WebRuleField> webRuleFields;
	
	public WebProcessor() {
		
	}
	
	public WebProcessor (Web web) {
		this.web = web;
	}
	
	public WebProcessor setWebRule (WebRule webRule) {
		// 构建 Json 对象
		this.webRule = webRule;
		this.webRuleFields = this.webRule.getWebRuleFields();
		return this;
	}
    
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me()
			.setRetryTimes(3)
			.setSleepTime(1000)
			.setTimeOut(10000)
			.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	
	@Override
	public Site getSite() {
		return site;
	}
    
    
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	@Override
	public void process(Page page) {
		
		if(page.getHtml().xpath(webRule.getListUrl()).match()){
			page.addTargetRequests(page.getHtml().xpath(webRule.getListUrl()).links().all());
		}else if (page.getHtml().xpath(webRule.getDetailUrl()).match()){
			page.addTargetRequests(page.getHtml().xpath(webRule.getDetailUrl()).links().all());
		}else{
			if(webRuleFields != null) {
				for (WebRuleField field : webRuleFields) {
					page.putField(field.getFieldKey(), page.getHtml().xpath(field.getFieldText()).toString());
				}
			}
		}
	}
	
}
