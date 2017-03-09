package com.peraglobal.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.peraglobal.mongodb.model.Metadata;

/**
 *  <code>MetadataRepository.java</code>
 *  <p>功能：元数据存储
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  @see 2016-12-19
 *  </br>最后修改人 无
 */
public interface MetadataRepository extends MongoRepository<Metadata, String> {
	
	/**
	 * 根据组爬虫 ID 获得元数据列表
	 * @param crawlerId 爬虫 ID
	 * @return List<Metadata> 元数据列表
	 */
    public List<Metadata> findByCrawlerId(String crawlerId);
   
    /**
	 * 根据 MD5 码 查询元数据
	 * @param md MD5 码
	 * @return Metadata 元数据
	 */
    public Metadata findByMd(String md);
    
	public void deleteByMetadataId(String metadataId);

	public void deleteByCrawlerId(String crawlerId);

	public Metadata findByMetadataId(String metadataId);
	
}
