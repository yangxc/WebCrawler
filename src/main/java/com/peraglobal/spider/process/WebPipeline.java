package com.peraglobal.spider.process;

import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
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
		
		// 采集到数据转换为 Json 格式
		String jsonData = JSONObject.toJSONString(resultItems.getAll().entrySet());
    	// 生成 MD5 码
		String md5 = IDGenerate.EncoderByMd5(jsonData);
			
		// 持久化元数据
		Metadata metadata = new Metadata();
		metadata.setCrawlerId(web.getCrawlerId());
		metadata.setMd(md5);
		metadata.setMetadata(jsonData);
		try {
			metadataService.createMetadata(metadata);
			// 监控日志
			historyService.updatePageCount(web.getCrawlerId());
		} catch (Exception e1) {
			historyService.updateExcetion(web.getCrawlerId(), e1.getMessage());
			e1.printStackTrace();
		}
		
		// 输出打印采集数据，开发是查看数据
		for (Entry<String, Object> entry : resultItems.getAll().entrySet()) {
        	System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }
	}

}
