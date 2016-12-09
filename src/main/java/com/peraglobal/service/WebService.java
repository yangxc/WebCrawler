package com.peraglobal.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peraglobal.mapper.WebMapper;
import com.peraglobal.model.Web;
import com.peraglobal.model.WebConst;

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
	 * @param webId  WEB 采集 ID
	 * @return Web  WEB 采集对象
	 * @throws Exception
	 */
	public Web getWeb(String webId) throws Exception {
		return webMapper.getWeb(webId);
	}

	/**
	 * 创建 WEB 采集
	 * @param WEB 采集对象
	 * @return webId  WEB 采集 ID
	 * @throws Exception
	 */
	public String createWeb(Web web) throws Exception {
		// 根据当前 WEB 采集名称和组 ID 查询是否存在，则不创建
		Web c = webMapper.getWebByWebName(web);
		if(c == null) {
			// uuid 任务 ID
			web.setWebId(java.util.UUID.randomUUID().toString());
			// 默认状态为：就绪
			web.setState(WebConst.STATE_READY);
			web.setCreateTime(new Date());
			web.setUpdateTime(new Date());
			webMapper.createWeb(web);
			return web.getWebId();
		}
		return null;
	}

	/**
	 * 通过 WEB 采集 ID 删除对象
	 * @param webId  WEB 采集 ID
	 * @throws Exception
	 */
	public void removeWeb(String webId) throws Exception {
		// 通过 WEB 采集 ID 查询对象是否存在
		Web c = webMapper.getWeb(webId);
		if(c != null) {
			// 判断 WEB 采集对象是否在运行，如果状态为：非就绪，则存在任务调度器中
			if(c.getState().equals(WebConst.STATE_READY)) {
				// 后续完善，停止任务
			}
			webMapper.removeWeb(webId);
		}
	}

	/**
	 * 编辑 WEB 采集对象
	 * @param WEB 采集对象
	 * @throws Exception
	 */
	public void editWeb(Web web) throws Exception {
		// 查询 WEB 采集对象是否存在
		Web c = webMapper.getWeb(web.getWebId());
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
	 * @param webId  WEB 采集 ID
	 * @throws Exception
	 */
	public void start(String webId) throws Exception {
		Web c = webMapper.getWeb(webId);
		//  WEB 采集状态为：非开始，则开始任务
		if(c != null && !c.getState().equals(WebConst.STATE_STRAT)) {
			// 更新任务状态
			c.setState(WebConst.STATE_STRAT);
			c.setUpdateTime(new Date());
			webMapper.updateStateByWeb(c);
		}
	}

	/**
	 * 停止 WEB 采集
	 * @param webId  WEB 采集 ID
	 * @throws Exception
	 */
	public void stop(String webId) throws Exception {
		Web t = webMapper.getWeb(webId);
		//  WEB 采集状态为：开始，则停止
		if(t != null && t.getState().equals(WebConst.STATE_STRAT)) {
			// 更新 WEB 采集状态为停止
			t.setState(WebConst.STATE_STOP);
			t.setUpdateTime(new Date());
			webMapper.updateStateByWeb(t);
		}
	}
}
