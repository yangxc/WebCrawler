package com.peraglobal.web.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peraglobal.spider.model.WebCrawler;
import com.peraglobal.web.mapper.WebMapper;
import com.peraglobal.web.model.Web;
import com.peraglobal.web.model.WebConst;

/**
 *  <code>WebService.java</code>
 *  <p>功能:WEB 采集功能 Service
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  2016-12-9
 *  </br>最后修改人 无
 */
@Service
public class WebService {
	
	@Autowired
    private WebMapper webMapper;
	
	@Autowired
	private SpiderService spiderService;
	
	/**
	 * 根据组 ID 查询 WEB 采集列表
	 * @param groupId 组 ID
	 * @return List<Web>  WEB 采集列表
	 * @throws Exception
	 */
	public List<Web> getWebList(String groupId) throws Exception {
		return webMapper.getWebList(groupId);
	}
	
	/**
	 * 根据 WEB 采集 ID 查询 WEB 采集对象
	 * @param crawlerId  WEB 采集 ID
	 * @return Web  WEB 采集对象
	 * @throws Exception
	 */
	public Web getWeb(String crawlerId) throws Exception {
		return webMapper.getWeb(crawlerId);
	}

	/**
	 * 创建 WEB 采集
	 * @param WEB 采集对象
	 * @return crawlerId  WEB 采集 ID
	 * @throws Exception
	 */
	public String createWeb(WebCrawler webCrawler) {
		Web web = new Web();
		web.setCrawlerName(webCrawler.getCrawlerName());
		web.setGroupId(webCrawler.getGroupId());
		web.setGroupName(webCrawler.getGroupName());
		
		// 根据当前 WEB 采集名称和组 ID 查询是否存在，则不创建
		Web c = webMapper.getWebByWebName(web);
		if(c == null) {
			// uuid 任务 ID
			if (null == webCrawler.getCrawlerId()) {
				web.setCrawlerId(java.util.UUID.randomUUID().toString());
			} else {
				web.setCrawlerId(webCrawler.getCrawlerId());
			}
			// 默认状态为：就绪
			web.setState(WebConst.STATE_READY);
			
			JSONObject jsonObj = new JSONObject(webCrawler.getWebRule());  
			web.setExpress(jsonObj.toString());
			
			web.setCreateTime(new Date());
			web.setUpdateTime(new Date());
			webMapper.createWeb(web);
			return web.getCrawlerId();
		}
		return null;
	}
	
	/**
	 * 通过 WEB 采集 ID 删除对象
	 * @param crawlerId  WEB 采集 ID
	 * @throws Exception
	 */
	public void removeWeb(String crawlerId) throws Exception {
		// 通过 WEB 采集 ID 查询对象是否存在
		Web c = webMapper.getWeb(crawlerId);
		if(c != null) {
			// 判断 WEB 采集对象是否在运行，如果状态为：非就绪，则存在任务调度器中
			if(c.getState().equals(WebConst.STATE_READY)) {
				// 后续完善，停止任务
			}
			webMapper.removeWeb(crawlerId);
		}
	}

	/**
	 * 编辑 WEB 采集对象
	 * @param WEB 采集对象
	 * @throws Exception
	 */
	public void editWeb(Web web) throws Exception {
		// 查询 WEB 采集对象是否存在
		Web c = webMapper.getWeb(web.getCrawlerId());
		if(c != null) {
			// 判断 WEB 采集对象是否在运行，如果状态为：非就绪，则存在任务调度器中
			if(c.getState().equals(WebConst.STATE_READY)) {
				// 后续完善，停止任务
			}
			web.setUpdateTime(new Date());
			webMapper.editWeb(web);
		}
	}

	/**
	 * 开始 WEB 采集
	 * @param crawlerId  WEB 采集 ID
	 * @throws Exception
	 */
	public void start(String crawlerId) throws Exception {
		Web c = webMapper.getWeb(crawlerId);
		//  WEB 采集状态为：非开始，则开始任务
		if(c != null && !c.getState().equals(WebConst.STATE_STRAT)) {
			// 更新任务状态
			c.setState(WebConst.STATE_STRAT);
			c.setUpdateTime(new Date());
			webMapper.updateStateByWeb(c);
			spiderService.start(c);
		}
	}

	/**
	 * 停止 WEB 采集
	 * @param crawlerId  WEB 采集 ID
	 * @throws Exception
	 */
	public void stop(String crawlerId) throws Exception {
		Web t = webMapper.getWeb(crawlerId);
		//  WEB 采集状态为：开始，则停止
		if(t != null && t.getState().equals(WebConst.STATE_STRAT)) {
			// 更新 WEB 采集状态为停止
			t.setState(WebConst.STATE_STOP);
			t.setUpdateTime(new Date());
			webMapper.updateStateByWeb(t);
		}
	}

	
}
