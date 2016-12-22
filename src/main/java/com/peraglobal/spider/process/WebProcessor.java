package com.peraglobal.spider.process;

import java.util.List;

import org.json.JSONObject;

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
		// 构建 Json 对象
		JSONObject jsonObj = new JSONObject(web.getExpress());  
		this.webRule = (WebRule)JSONObject.wrap(jsonObj);
		this.webRuleFields = this.webRule.getWebRuleFields();
	}
    
	private Site site = Site.me()
			.setRetryTimes(webRule.getRetryTimes() != 0 ? webRule.getRetryTimes() : 3)
			.setSleepTime(webRule.getSleepTime() != 0 ? webRule.getSleepTime() : 1000)
			.setTimeOut(10000)
			.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	
	@Override
	public Site getSite() {
		return site;
	}
	
	/*
    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }
    */
	
	@Override
	public void process(Page page) {
		
		if (webRuleFields != null) {
			for (WebRuleField field : webRuleFields) {
				
				if (WebConst.XPATH.equals(field.getFieldType())) { // xpath
					if (WebConst.ADD.equals(field.getType())) { 
						// addTargetRequests
						page.addTargetRequests(page.getHtml().links().xpath(field.getFieldText()).all());
					} else { 
						// putField
						page.putField(field.getFieldKey(), page.getHtml().xpath(field.getFieldText()).toString());
					}
				} else if (WebConst.REGEX.equals(field.getFieldType())) { // regex
					if (WebConst.ADD.equals(field.getType())) {
						page.addTargetRequests(page.getHtml().links().regex(field.getFieldText()).all());
					} else {
						page.putField(field.getFieldKey(), page.getUrl().regex(field.getFieldText()).toString());
					}
				} else { // css 
					if (WebConst.ADD.equals(field.getType())) {
						page.addTargetRequests(page.getHtml().links().css(field.getFieldText()).all());
					} else {
						page.putField(field.getFieldKey(), page.getUrl().css(field.getFieldText()).toString());
					}
				}
			}
		}
	}
}
