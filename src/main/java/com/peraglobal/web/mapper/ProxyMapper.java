package com.peraglobal.web.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.peraglobal.web.model.Proxy;

/**
 *  <code>ProxyMapper.java</code>
 *  <p>功能：代理
 *  
 *  <p>Copyright 安世亚太 2017 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  @see 2017-2-23
 *  </br>最后修改人 无
 */
@Mapper
public interface ProxyMapper {
	
	@Select("select * from proxy where crawlerId = #{crawlerId}")
    public Proxy getProxyByCrawlerId(String crawlerId);
   
	/**
	 * 根据 ID 查询代理
	 * @param id 代理 ID
	 * @return Proxy 代理
	 */
    @Select("select * from proxy where proxyId = #{proxyId}")
    public Proxy getProxy(String proxyId);
    
    /**
	 * 创建历史记录
	 * @param Proxy 代理
	 */
    @Insert("insert into proxy (proxyId, crawlerId, hostName, port, createTime, updateTime) values (#{proxyId}, #{crawlerId}, #{hostName}, #{port}, #{createTime}, #{updateTime})")  
    public void createProxy(Proxy proxy);
    
    /**
	 * 移除代理
	 * @param proxyId 代理 ID
	 */
    @Delete("delete from proxy where proxyId = #{proxyId}")
	public void removeProxy(String proxyId);
    
    /**
	 * 移除代理
	 * @param proxyId 代理 ID
	 */
    @Delete("delete from proxy where crawlerId = #{crawlerId}")
	public void removeProxyByCrawlerId(String crawlerId);
    
    /**
	 * 编辑历史记录采集数量
	 * @param history 历史记录对象
	 */
    @Update("update proxy set hostName = #{hostName}, port = #{port}, updateTime = #{updateTime} where proxyId = #{proxyId}")
	public void editProxy(Proxy proxy);

    
}
