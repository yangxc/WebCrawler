package com.peraglobal.web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peraglobal.common.IDGenerate;
import com.peraglobal.web.mapper.ProxyMapper;
import com.peraglobal.web.model.Proxy;

/**
 *  <code>ProxyService.java</code>
 *  <p>功能：代理功能 Service
 *  
 *  <p>Copyright 安世亚太 2017 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  2017-2-23
 *  </br>最后修改人 无
 */
@Service
public class ProxyService {
	
	@Autowired
    private ProxyMapper proxyMapper;

	
	public Proxy getProxyByCrawlerId(String crawlerId) throws Exception {
		return proxyMapper.getProxyByCrawlerId(crawlerId);
	}
	
	public Proxy getProxy(String proxyId) throws Exception {
		return proxyMapper.getProxy(proxyId);
	}

	public String createProxy(Proxy proxy) throws Exception {
		proxy.setProxyId(IDGenerate.uuid());
		proxy.setCreateTime(new Date());
		proxy.setUpdateTime(new Date());
		proxyMapper.createProxy(proxy);
		return proxy.getProxyId();
	}

	public void removeProxy(String proxyId) throws Exception {
		proxyMapper.removeProxy(proxyId);
	}
	
	public void removeProxyByCrawlerId(String crawlerId) throws Exception {
		proxyMapper.removeProxyByCrawlerId(crawlerId);
	}
	
	public void editProxy(Proxy proxy) throws Exception {
		proxyMapper.editProxy(proxy);
	}

}
