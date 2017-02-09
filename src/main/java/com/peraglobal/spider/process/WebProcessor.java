package com.peraglobal.spider.process;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.peraglobal.common.CurrentApplicationContext;
import com.peraglobal.common.IDGenerate;
import com.peraglobal.mongodb.model.Attachment;
import com.peraglobal.mongodb.repository.AttachmentRepository;
import com.peraglobal.spider.model.WebConst;
import com.peraglobal.spider.model.WebRule;
import com.peraglobal.spider.model.WebRuleField;
import com.peraglobal.web.model.Metadata;
import com.peraglobal.web.model.Web;
import com.peraglobal.web.service.HistoryService;
import com.peraglobal.web.service.MetadataService;

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
				// 元数据对象
				StringBuffer sbData = new StringBuffer();
				// 附件对象
				Attachment attachment = null;
				for (WebRuleField field : webRuleFields) {
					String text = page.getHtml().xpath(field.getFieldText()).toString();
					
					// 附件下载功能
					if (text.indexOf("href") != -1) {
						try {
							Request request = new Request();
							request.setUrl(page.getHtml().xpath(field.getFieldText()).links().toString());
							Page pageDown = new WebDownloader().downloads(request, site);
							byte[] contexts = pageDown.getContentBytes();
							if (contexts != null) {
								attachment = new Attachment();
								
								// 附件内容
								attachment.setContext(contexts);
								// 附件地址
								attachment.setFilePath(pageDown.getUrl().toString());
								// 附件名称
								attachment.setFileName(pageDown.getFileName());
								// 附件类型
								attachment.setFileType(pageDown.getFileType());
							}
						} catch (Exception e) {
						}
					}else {
						// 普通属性功能
						page.putField(field.getFieldKey(), text);
						sbData.append(field.getFieldKey()).append(":\t").append(text).append("\t\r");
					}
				}
				
				// 保存元数据
				if (true) {
					// 保存元数据
					// 采集到数据转换为 Json 格式
					String jsonData = JSONObject.toJSONString(sbData);
			    	
					// 生成 MD5 码
					String md5 = IDGenerate.EncoderByMd5(jsonData);
						
					// 持久化元数据
					Metadata metadata = new Metadata();
					metadata.setCrawlerId(web.getCrawlerId());
					metadata.setMd(md5);
					metadata.setMetadata(jsonData);
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
}
