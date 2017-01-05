package com.peraglobal.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.peraglobal.web.model.History;
import com.peraglobal.web.model.Metadata;
import com.peraglobal.spider.model.WebCrawler;
import com.peraglobal.web.model.Web;
import com.peraglobal.web.service.HistoryService;
import com.peraglobal.web.service.MetadataService;
import com.peraglobal.web.service.WebService;


/**
 *  <code>WebController.java</code>
 *  <p>功能:WEB 采集 Controller
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
@RestController
@RequestMapping("web")
public class WebController {
	
	@Autowired
	private WebService webService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private MetadataService metadataService;
	
	/**
	 * 获得 Web 采集列表
	 * @param groupId 组Id （多用户区分不同用户）
	 * @return List<Web> Web 采集列表
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getWebList/{groupId}", method = RequestMethod.GET)
	public ResponseEntity<List<Web>> getWebList(@PathVariable("groupId") String groupId) {
		try {
			List<Web> webs = webService.getWebList(groupId);
			return new ResponseEntity<>(HttpStatus.OK).accepted().body(webs);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	/**
	 * 获得 Web 采集
	 * @param crawlerId Web 采集 ID
	 * @return Web 采集
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getWeb/{crawlerId}", method = RequestMethod.GET)
	public ResponseEntity<Web> getWeb(@PathVariable("crawlerId") String crawlerId) {
		try {
			Web web = webService.getWeb(crawlerId);
			return new ResponseEntity<>(HttpStatus.OK).accepted().body(web);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	/**
	 * 创建 Web 采集
	 * @param crawler webCrawler 采集对象
	 * @return crawlerId 创建成功返回 Web 采集 ID
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/createWeb", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createWeb(@RequestBody WebCrawler webCrawler) {
		try {
			String crawlerId = webService.createWeb(webCrawler);
			if(crawlerId != null) {
				return new ResponseEntity<>(HttpStatus.CREATED).accepted().body(crawlerId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * 移除 Web 采集
	 * @param crawlerId Web 采集 ID
	 * @return 状态码
	 * @since 1.0
	 */
	@RequestMapping(value = "/removeWeb/{crawlerId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeWeb(@PathVariable("crawlerId") String crawlerId) {
		try {
			webService.removeWeb(crawlerId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * 编辑 Web 采集
	 * @param web 采集对象
	 * @return 状态码
	 * @since 1.0
	 */
	@RequestMapping(value = "/editWeb", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editWeb(@RequestBody WebCrawler webCrawler) {
		try {
			webService.editWeb(webCrawler);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * 开始 Web 采集
	 * @param Web 采集对象
	 * @return 状态码
	 * @since 1.0
	 */
	@RequestMapping(value = "/start", method = RequestMethod.PUT)
	public ResponseEntity<?> start(@RequestBody String crawlerId) {
		try {
			webService.start(crawlerId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * 停止 Web 采集
	 * @see 2016-12-7	 
	 * @param Web 采集
	 * @return 状态码
	 * @since 1.0
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.PUT)
	public ResponseEntity<?> stop(@RequestBody String crawlerId) {
		try {
			webService.stop(crawlerId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * 根据爬虫 ID 获得数据库采集历史记录
	 * @param crawlerId 爬虫 ID
	 * @return List<History> 历史记录
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getHistoryByCrawlerId/{crawlerId}", method = RequestMethod.GET)
	public ResponseEntity<List<History>> getHistoryByCrawlerId(@PathVariable("crawlerId") String crawlerId) {
		try {
			List<History> historys = historyService.getHistorysByCrawlerId(crawlerId);
			return new ResponseEntity<>(HttpStatus.OK).accepted().body(historys);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	/**
	 * 根据爬虫ID 获得任务采集数量
	 * @param crawlerId 爬虫 ID
	 * @return Integer 采集数量
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getCountByCrawlerId/{crawlerId}", method = RequestMethod.GET)
	public ResponseEntity<Integer> getCountByCrawlerId(@PathVariable("crawlerId") String crawlerId) {
		try {
			int count = historyService.getCountByCrawlerId(crawlerId);
			return new ResponseEntity<>(HttpStatus.OK).accepted().body(count);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	/**
	 * 根据爬虫ID 获得元数据
	 * @param crawlerId 爬虫 ID
	 * @return List<Metadata> 元数据
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getMetadataByCrawlerId/{crawlerId}", method = RequestMethod.GET)
	public ResponseEntity<List<Metadata>> getMetadataByCrawlerId(@PathVariable("crawlerId") String crawlerId) {
		try {
			List<Metadata> metadatas = metadataService.getMetadatasByCrawlerId(crawlerId);
			return new ResponseEntity<>(HttpStatus.OK).accepted().body(metadatas);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}