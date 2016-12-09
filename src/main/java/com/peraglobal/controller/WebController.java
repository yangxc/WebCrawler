package com.peraglobal.controller;

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

import com.peraglobal.model.Web;
import com.peraglobal.service.WebService;


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
	 * @param webId Web 采集 ID
	 * @return Web 采集
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getWeb/{webId}", method = RequestMethod.GET)
	public ResponseEntity<Web> getWeb(@PathVariable("webId") String webId) {
		try {
			Web web = webService.getWeb(webId);
			return new ResponseEntity<>(HttpStatus.OK).accepted().body(web);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	/**
	 * 创建 Web 采集
	 * @param crawler Web 采集对象
	 * @return crawlerId 创建成功返回 Web 采集 ID
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/createWeb", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createWeb(@RequestBody Web web) {
		try {
			String webId = webService.createWeb(web);
			if(webId != null) {
				return new ResponseEntity<>(HttpStatus.CREATED).accepted().body(webId);
			}
		} catch (Exception e) {}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * 移除 Web 采集
	 * @param webId Web 采集 ID
	 * @return 状态码
	 * @since 1.0
	 */
	@RequestMapping(value = "/removeWeb/{webId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeWeb(@PathVariable("webId") String webId) {
		try {
			webService.removeWeb(webId);
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
	public ResponseEntity<?> editWeb(@RequestBody Web web) {
		try {
			webService.editWeb(web);
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
	@RequestMapping(value = "/start", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> start(@RequestBody Web web) {
		try {
			webService.start(web.getWebId());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * 停止 Web 采集
	 * @see 2016-12-7	 
	 * @param Web 采集
	 * @return 状态码
	 * @since 1.0
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> stop(@RequestBody Web web) {
		try {
			webService.stop(web.getWebId());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
}