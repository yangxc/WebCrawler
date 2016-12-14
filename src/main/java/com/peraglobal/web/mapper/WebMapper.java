package com.peraglobal.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.peraglobal.web.model.Web;

/**
 *  <code>WebMapper.java</code>
 *  <p>功能:WEB采集存储
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  @see 2016-12-9
 *  </br>最后修改人 无
 */
@Mapper
public interface WebMapper {
	
	/**
	 * 根据组 ID 获得 WEB 采集列表
	 * @param groupId 组 ID
	 * @return List<Crawler> 任务列表
	 */
	@Select("select * from Web where groupId = #{groupId}")
    public List<Web> getWebList(String groupId);
   
	/**
	 * 根据 ID  WEB 采集
	 * @param webId  WEB 采集 ID
	 * @return Web 任务
	 */
    @Select("select * from Web where webId = #{webId}")
    public Web getWeb(String webId);
   
	/**
	 * 根据 WEB 采集名称和组 ID  WEB 采集
	 * @param webName  WEB 采集名称
	 * @param groupId 组 ID
	 * @return Web  WEB 采集
	 */
    @Select("select * from Web where webName = #{webName} and groupId = #{groupId}")
    public Web getWebByWebName(Web web);
    
	/**
	 * 创建 WEB 采集
	 * @param Web  WEB 采集对象
	 */
    @Insert("insert into Web (webId, webName, groupId, groupName, express, state, createTime, updateTime) values (#{webId}, #{webName}, #{groupId}, #{groupName}, #{express}, #{state}, #{createTime}, #{updateTime})")  
    public void createWeb(Web web);

    /**
	 * 移除 WEB 采集
	 * @param webId 任务 ID
	 */
    @Delete("delete from Web where webId = #{webId}")
	public void removeWeb(String webId);

	/**
	 * 编辑 WEB 采集
	 * @param Web  WEB 采集对象
	 */
    @Update("update Web set webName = #{webName}, groupName = #{groupName}, express = #{express}, updateTime = #{updateTime} where webId = #{webId}")
	public void editWeb(Web web);

	/**
	 * 根据 WEB 采集 ID 修改状态
	 * @param Web  WEB 采集对象
	 */
    @Update("update Web set state = #{state}, updateTime = #{updateTime} where webId = #{webId}")
	public int updateStateByWeb(Web web);

}
