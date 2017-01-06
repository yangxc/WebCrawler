package com.peraglobal.spider.process;

import java.util.List;

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
	
	
    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            //page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }
    
    /*
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	@Override
	public void process(Page page) {
		
		if (webRuleFields != null) {
			for (WebRuleField field : webRuleFields) {
				
				if (WebConst.XPATH.equals(field.getFieldType())) { // xpath
					if (WebConst.ADD.equals(field.getType())) { 
						// addTargetRequests // 部分三：从页面发现后续的url地址来抓取
						page.addTargetRequests(page.getHtml().links().xpath(field.getFieldText()).all());
					} else { 
						// putField // 部分二：定义如何抽取页面信息，并保存下来
						page.putField(field.getFieldKey(), page.getHtml().xpath(field.getFieldText()).toString());
					}
					
				} else if (WebConst.REGEX.equals(field.getFieldType())) { // regex
					if (WebConst.ADD.equals(field.getType())) {
						// 部分三：从页面发现后续的url地址来抓取
						page.addTargetRequests(page.getHtml().links().regex(field.getFieldText()).all());
					} else {
						// 部分二：定义如何抽取页面信息，并保存下来
						page.putField(field.getFieldKey(), page.getUrl().regex(field.getFieldText()).toString());
					}
					
				} else { // css 
					if (WebConst.ADD.equals(field.getType())) {
						// 部分三：从页面发现后续的url地址来抓取
						page.addTargetRequests(page.getHtml().links().css(field.getFieldText()).all());
					} else {
						// 部分二：定义如何抽取页面信息，并保存下来
						page.putField(field.getFieldKey(), page.getUrl().css(field.getFieldText()).toString());
					}
				}
			}
		}
	}
	*/
}
