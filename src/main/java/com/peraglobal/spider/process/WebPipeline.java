package com.peraglobal.spider.process;

import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.peraglobal.common.CurrentApplicationContext;
import com.peraglobal.common.IDGenerate;
import com.peraglobal.web.model.Metadata;
import com.peraglobal.web.model.Web;
import com.peraglobal.web.service.HistoryService;
import com.peraglobal.web.service.MetadataService;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class WebPipeline implements Pipeline{
	
	Web web;
	
	@Autowired
    private HistoryService historyService;
	
	@Autowired
    private MetadataService metadataService;
	
	public WebPipeline() {
	}

	public WebPipeline(Web web) {
		this.web = web;
		this.historyService = (HistoryService) CurrentApplicationContext.getBean("historyService");
		this.metadataService = (MetadataService) CurrentApplicationContext.getBean("metadataService");
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		System.out.println("get page: " + resultItems.getRequest().getUrl());
        for (Entry<String, Object> entry : resultItems.getAll().entrySet()) {
        	try {
	        	System.out.println(entry.getKey() + ":\t" + entry.getValue());
	        	// 生成 MD5 码
				String md5 = IDGenerate.EncoderByMd5(entry.getKey() + ":\t" + entry.getValue());
				// 判断数据是否存在
				Metadata metadata = metadataService.getMetadataByMd(md5);
				if (metadata == null) {
					
					// 持久化元数据
					metadata = new Metadata();
					metadata.setCrawlerId(web.getCrawlerId());
					metadata.setMd(md5);
					metadata.setMetadata(entry.getKey() + ":\t" + entry.getValue());
					metadataService.createMetadata(metadata);
					
					// 监控日志，后续完善
					historyService.updatePageCount(web.getCrawlerId());
				}
        	} catch (Exception e) {
				e.printStackTrace();
				historyService.updateExcetion(web.getCrawlerId(), e.getMessage());
			}
        }
	}

}
