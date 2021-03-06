package com.peraglobal.spider.process;

import java.util.List;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.peraglobal.common.CurrentApplicationContext;
import com.peraglobal.common.IDGenerate;
import com.peraglobal.mongodb.model.Attachment;
import com.peraglobal.mongodb.model.Metadata;
import com.peraglobal.mongodb.repository.AttachmentRepository;
import com.peraglobal.mongodb.service.MetadataService;
import com.peraglobal.spider.model.AttachmentRule;
import com.peraglobal.spider.model.WebConst;
import com.peraglobal.spider.model.WebRule;
import com.peraglobal.spider.model.WebRuleField;
import com.peraglobal.web.model.Proxy;
import com.peraglobal.web.model.Web;
import com.peraglobal.web.service.HistoryService;

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
	Proxy proxy;
	WebRule webRule;
	AttachmentRule attachmentRule;
	
	List<WebRuleField> webRuleFields;
	
	@Autowired
    private HistoryService historyService;
	
	@Autowired
    private MetadataService metadataService;
	
	@Autowired
    private AttachmentRepository attachmentRepository;
	
	public WebProcessor() {
		
	}
	
	public WebProcessor (Web web) {
		this.web = web;
		this.historyService = (HistoryService) CurrentApplicationContext.getBean("historyService");
		this.metadataService = (MetadataService) CurrentApplicationContext.getBean("metadataService");
		this.attachmentRepository = (AttachmentRepository) CurrentApplicationContext.getBean("attachmentRepository");
	}
	
	public WebProcessor setProxy (Proxy proxy) {
		this.proxy = proxy;
		return this;
	}
	
	public WebProcessor setWebRule (WebRule webRule) {
		// 构建 Json 对象
		this.webRule = webRule;
		this.webRuleFields = this.webRule.getWebRuleFields();
		if (webRule.getAttachmentRule() != null) {
			attachmentRule = webRule.getAttachmentRule();
		}
		return this;
	}
    
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me()
			.setRetryTimes(3)
			.setSleepTime(1000)
			.setTimeOut(10000)
			.setHttpProxy(proxy ==null ? null : new HttpHost(proxy.getHostName(), proxy.getPort()))
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
				if(page.getHtml().xpath(list_url).match()){
					list = true;
					page.addTargetRequests(page.getHtml().xpath(list_url).links().all());
				}
			}else if (list_type.equals(WebConst.REGEX)) {
				if(page.getHtml().regex(list_url).match()){
					list = true;
					page.addTargetRequests(page.getHtml().regex(list_url).links().all());
				}
			}else {
				if(page.getHtml().css(list_url).match()){
					list = true;
					page.addTargetRequests(page.getHtml().css(list_url).links().all());
				}
			}
		}
		
		// 详情规则处理，如果是一个页面获得详情地址处理
		if (detail_url != null && !"".equals(detail_url)) {
			
			if (detail_type.equals(WebConst.XPATH)) {
				if(page.getHtml().xpath(detail_url).match()){
					detail = true;
					page.addTargetRequests(page.getHtml().xpath(detail_url).links().all());
					
				}
			}else if (detail_type.equals(WebConst.REGEX)) {
				if(page.getHtml().regex(detail_url).match()){
					detail = true;
					page.addTargetRequests(page.getHtml().regex(detail_url).links().all());
				}
			}else {
				if(page.getHtml().css(detail_url).match()){
					detail = true;
					page.addTargetRequests(page.getHtml().css(detail_url).links().all());
				}
			}
		}
		
		// 属性规则处理，如果具体的页面属性处理
		if (!list && !detail) {

			// 附件对象
			Attachment attachment = null;
			
			// 附件下载解析
			if (attachmentRule != null) {
				try {
					Request request = new Request();
					// 设置附件采集 request 信息
					if (attachmentRule.getAttachmentType().equals(WebConst.XPATH)) {
						request.setUrl(page.getHtml().xpath(attachmentRule.getAttachmentKey()).links().toString());
					}else if (attachmentRule.getAttachmentType().equals(WebConst.REGEX)) {
						request.setUrl(page.getHtml().regex(attachmentRule.getAttachmentKey()).links().toString());
					}else {
						request.setUrl(page.getHtml().regex(attachmentRule.getAttachmentKey()).links().toString());
					}
					Page pageDown = new WebDownloader().downloads(request, site);
					byte[] contexts = pageDown.getContentBytes();
					if (contexts != null) {
						attachment = new Attachment();
						// 附件内容
						attachment.setContext(contexts);
						// 附件 URL
						attachment.setUrl(pageDown.getUrl().get());
						// 附件名称
						attachment.setFileName(pageDown.getFileName());
						// 附件类型
						attachment.setFileType(pageDown.getFileType());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 元数据 Json 对象
			StringBuffer sbData = new StringBuffer();
			
			// 元数据 key value 对象
			DBObject kvs = null;
			
			// 属性规则文件解析
			if(webRuleFields != null) {
				kvs = new BasicDBObject();
				try {
					for (WebRuleField field : webRuleFields) {
						String text = "";
						if (field.getFieldType().equals(WebConst.XPATH)) {
							text = page.getHtml().xpath(field.getFieldText()).get();
						}else if (field.getFieldType().equals(WebConst.REGEX)) {
							text = page.getHtml().regex(field.getFieldText()).get();
						}else {
							text = page.getHtml().css(field.getFieldText()).get();
						}
						
						// 填充数据
						if (text != null) {
							// 普通属性功能
							page.putField(field.getFieldKey(), text);
							kvs.put(field.getFieldKey(), text);
							sbData.append(field.getFieldKey()).append(":\t").append(text).append("\t\r");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// 保存元数据
			if (sbData.length() > 0 || attachment != null) {
				
				// 采集到数据转换为 Json 格式
				String jsonData = JSONObject.toJSONString(sbData);
		    	
				// 生成 MD5 码
				String md5 = IDGenerate.EncoderByMd5(jsonData);
				
				if (jsonData == null && "".equals(jsonData)) {
					md5 = IDGenerate.EncoderByMd5(page.getRawText());
				}
				
				// 持久化元数据
				Metadata metadata = new Metadata();
				metadata.setCrawlerId(web.getCrawlerId());
				metadata.setMd(md5);
				metadata.setUrl(page.getUrl().get()); // url 记录
				metadata.setHtmlMeta(page.getRawText().getBytes()); // HTML 元数据
				metadata.setKvs(kvs);
				metadata.setJsonData(jsonData);
				try {
					String metadataId = metadataService.createMetadata(metadata);
					// 保存附件
					if (attachment != null) {
						attachment.setCrawlerId(web.getCrawlerId());
						attachment.setMetadataId(metadataId);
						attachmentRepository.save(attachment);
					}
					// 监控日志
					historyService.updatePageCount(web.getCrawlerId());
				} catch (Exception e1) {
					historyService.updateExcetion(web.getCrawlerId(), e1.getMessage());
					e1.printStackTrace();
				}
			}
		}
	}
}
