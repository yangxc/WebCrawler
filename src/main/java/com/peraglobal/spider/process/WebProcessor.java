package com.peraglobal.spider.process;

import java.util.List;

import com.peraglobal.spider.model.WebConst;
import com.peraglobal.spider.model.WebRule;
import com.peraglobal.spider.model.WebRuleField;
import com.peraglobal.web.model.Web;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.WebDownloader;
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
		String list_url = webRule.getListUrl(); // 列表url
		String list_type = webRule.getListUrlType(); // 列表规则类型
		String detail_url = webRule.getDetailUrl(); // 详情url
		String detail_type = webRule.getDetailUrlType(); // 详情规则类型
		
		boolean list = false; // 是否是解析 list 页面
		boolean detail = false; // 是否解析 详情 list 页面
		
		// 列表规则处理，如果是每一页列表页面处理
		if (list_url != null && !"".equals(list_url)) {
			if (list_type.equals(WebConst.XPATH)) {
				if(page.getHtml().xpath(webRule.getListUrl()).match()){
					page.addTargetRequests(page.getHtml().xpath(webRule.getListUrl()).links().all());
					list = true;
				}
			}else {
				if(page.getHtml().regex(webRule.getListUrl()).match()){
					page.addTargetRequests(page.getHtml().regex(webRule.getListUrl()).links().all());
					list = true;
				}
			}
		}
		
		// 详情规则处理，如果是一个页面获得详情地址处理
		if (detail_url != null && !"".equals(detail_url) &&
				!list) {
			if (detail_type.equals(WebConst.XPATH)) {
				if(page.getHtml().xpath(webRule.getDetailUrl()).match()){
					page.addTargetRequests(page.getHtml().xpath(webRule.getDetailUrl()).links().all());
					detail = true;
				}
			}else {
				if(page.getHtml().regex(webRule.getDetailUrl()).match()){
					page.addTargetRequests(page.getHtml().regex(webRule.getDetailUrl()).links().all());
					detail = true;
				}
			}
		}
		
		// 属性规则处理，如果具体的页面属性处理
		if (!list && !detail) {
			if(webRuleFields != null) {
				for (WebRuleField field : webRuleFields) {
					String context = page.getHtml().xpath(field.getFieldText()).toString();
					if (context.indexOf("href") != -1) {
						// 附件下载功能
						System.out.println(page.getHtml().xpath(field.getFieldText()).links());
						Request request = new Request();
						request.setUrl(page.getHtml().xpath(field.getFieldText()).links().toString());
						Page pageDown = new WebDownloader().downloads(request, site);
						byte[] cent = pageDown.getContentBytes();
						// 后续开发
					}else {
						// 普通属性功能
						page.putField(field.getFieldKey(), context);
					}
				}
			}
		}
	}
}
